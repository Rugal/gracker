package ga.rugal.gracker.core.dao;

import java.io.IOException;

import ga.rugal.gracker.core.entity.RawIssue;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Interface for Tree.
 *
 * @author Rugal Bernstein
 */
public interface TreeDao {

  /**
   * Create tree from raw issue content.
   *
   * @param content raw issue content
   *
   * @return object id
   *
   * @throws IOException unable to write to file system
   */
  ObjectId create(RawIssue.Content content) throws IOException;

  /**
   * Read a tree object.
   *
   * @param blobId object id
   *
   * @return tree walk object
   *
   * @throws IOException unable to read from file system
   */
  TreeWalk read(ObjectId blobId) throws IOException;
}
