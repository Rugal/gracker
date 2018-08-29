package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.rugal.gracker.core.dao.TreeDao;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Object repository implementation.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
public class TreeDaoImpl implements TreeDao {

  @Autowired
  private Repository repository;

  @Override
  public ObjectId create(final String fileName, final ObjectId blobId) throws IOException {
    final ObjectInserter objectInserter = this.repository.newObjectInserter();
    final TreeFormatter treeFormatter = new TreeFormatter();
    treeFormatter.append(fileName, FileMode.REGULAR_FILE, blobId);
    final ObjectId treeId = objectInserter.insert(treeFormatter);
    objectInserter.flush();
    return treeId;
  }

  @Override
  public TreeWalk read(final ObjectId blobId) throws IOException {
    final TreeWalk treeWalk = new TreeWalk(this.repository);
    treeWalk.addTree(blobId);
    treeWalk.next();
    return treeWalk;
  }
}
