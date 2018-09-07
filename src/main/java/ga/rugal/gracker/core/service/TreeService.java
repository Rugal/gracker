package ga.rugal.gracker.core.service;

import java.io.IOException;

import ga.rugal.gracker.core.dao.TreeDao;
import ga.rugal.gracker.core.entity.Issue;

import org.eclipse.jgit.lib.ObjectId;

/**
 * Interface for commit service.
 *
 * @author Rugal Bernstein
 */
public interface TreeService extends ServiceBase<TreeDao> {

  /**
   * Read tree object into {@link Issue.Content} object.
   *
   * @param treeId tree object
   *
   * @return Issue.Content object
   *
   * @throws IOException unable to read fro file system
   */
  Issue.Content read(ObjectId treeId) throws IOException;
}
