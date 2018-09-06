package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.dao.TreeDao;
import ga.rugal.gracker.core.entity.RawIssue;

import lombok.Setter;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation for tree repository.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
public class TreeDaoImpl implements TreeDao {

  @Autowired
  @Setter
  private Repository repository;

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectId create(final RawIssue.Content content) throws IOException {
    final ObjectInserter inserter = this.repository.newObjectInserter();
    final TreeFormatter treeFormatter = new TreeFormatter();
    treeFormatter.append(SystemDefaultProperty.TITLE, FileMode.REGULAR_FILE, content.getTitle());
    treeFormatter.append(SystemDefaultProperty.BODY, FileMode.REGULAR_FILE, content.getBody());
    if (null != content.getLabel()) {
      treeFormatter.append(SystemDefaultProperty.LABEL, FileMode.REGULAR_FILE, content.getLabel());
    }
    final ObjectId treeId = inserter.insert(treeFormatter);
    inserter.flush();
    return treeId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TreeWalk read(final ObjectId blobId) throws IOException {
    final TreeWalk treeWalk = new TreeWalk(this.repository);
    treeWalk.addTree(blobId);
    treeWalk.next();
    return treeWalk;
  }
}
