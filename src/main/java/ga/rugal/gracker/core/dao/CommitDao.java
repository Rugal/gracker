package ga.rugal.gracker.core.dao;

import java.io.IOException;

import ga.rugal.gracker.core.entity.Issue;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Interface for commit.
 *
 * @author Rugal Bernstein
 */
public interface CommitDao {

  /**
   * Create commit from commit information and tree content.
   *
   * @param commit the commit information to be persisted
   * @param treeId the issue tree object id
   *
   * @return the commit object id
   *
   * @throws IOException unable to write to file system
   */
  ObjectId create(Issue.Commit commit, ObjectId treeId) throws IOException;

  /**
   * Update commit.<BR>
   * This will create a new commit that points to the parent commit.
   *
   * @param commit   the commit information to be persisted
   * @param treeId   the issue tree object id
   * @param parentId parent commit object id
   *
   * @return the commit object id
   *
   * @throws IOException unable to write to file system
   */
  ObjectId update(Issue.Commit commit, ObjectId treeId, ObjectId parentId) throws IOException;

  /**
   * Read commit object from file system.
   *
   * @param commit the commit object
   *
   * @return native revision commit object
   *
   * @throws IOException unable to read from file system
   */
  RevCommit read(ObjectId commit) throws IOException;
}
