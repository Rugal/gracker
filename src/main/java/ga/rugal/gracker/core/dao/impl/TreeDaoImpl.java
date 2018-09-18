package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import config.Constant;

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
    //Must follow alphabetic order, otherwise git fsck will fail
    treeFormatter.append(Constant.BODY, FileMode.REGULAR_FILE, content.getBody());
    if (null != content.getLabel()) {
      treeFormatter.append(Constant.LABEL, FileMode.REGULAR_FILE, content.getLabel());
    }
    treeFormatter.append(Constant.TITLE, FileMode.REGULAR_FILE, content.getTitle());
    final ObjectId treeId = inserter.insert(treeFormatter);
    inserter.flush();
    return treeId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TreeWalk read(final ObjectId treeId) throws IOException {
    final TreeWalk treeWalk = new TreeWalk(this.repository);
    treeWalk.addTree(treeId);
    treeWalk.setRecursive(false);
    return treeWalk;
  }
}
