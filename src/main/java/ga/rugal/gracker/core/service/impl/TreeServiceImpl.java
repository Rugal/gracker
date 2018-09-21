package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.Arrays;

import config.Constant;

import ga.rugal.gracker.core.dao.TreeDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.service.BlobService;
import ga.rugal.gracker.core.service.TreeService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for tree service.
 *
 * @author Rugal Bernstein
 */
@Service
@Slf4j
public class TreeServiceImpl implements TreeService {

  @Autowired
  @Getter
  @Setter
  private TreeDao dao;

  @Autowired
  @Setter
  private BlobService blobService;

  /**
   * {@inheritDoc}
   */
  @Override
  public Issue.Content read(final ObjectId treeId) throws IOException {
    LOG.debug("Process tree object [{}]", treeId.getName());
    final TreeWalk treeWalk = this.dao.read(treeId);
    final Issue.Content content = new Issue.Content();
    while (treeWalk.next()) {
      final ObjectId objectId = treeWalk.getObjectId(0);
      final String key = treeWalk.getPathString();
      final String value = this.blobService.read(objectId);
      LOG.debug("Process blob object [{}] key [{}] value [{}]", objectId.getName(), key, value);
      if (Constant.LABEL.equals(treeWalk.getPathString())) {
        content.setLabel(Arrays.asList(value.split(",")));
        LOG.debug("Process blob object [{}] with {} label(s)",
                  objectId.getName(),
                  content.getLabel().size());
        continue;
      }
      //Set non-label content
      content.set(key, value);
    }
    return content;
  }
}
