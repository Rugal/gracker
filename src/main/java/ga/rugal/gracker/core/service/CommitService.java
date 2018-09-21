package ga.rugal.gracker.core.service;

import java.io.IOException;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.exception.IssueNotFoundException;

import org.eclipse.jgit.lib.ObjectId;

/**
 * Interface for commit service.
 *
 * @author Rugal Bernstein
 */
public interface CommitService extends ServiceBase<CommitDao> {

  /**
   * Create issue only.
   *
   * @param issue content
   *
   * @return created raw issue object
   *
   * @throws IOException unable to write to file system
   */
  RawIssue create(Issue issue) throws IOException;

  /**
   * Update a commit.<BR>
   * Similar to {@link #create(ga.rugal.gracker.core.entity.Issue)} except it should keep track of
   * the commit parent.
   *
   * @param issue the issue object to be updated
   *
   * @return created raw issue object
   *
   * @throws IOException            unable to write to file system
   * @throws IssueNotFoundException unable to find parent commit
   */
  RawIssue update(Issue issue) throws IOException, IssueNotFoundException;

  /**
   * Read and parse commit object from repository with issue id set.<BR>
   * This method reads any commit object and traces back to the root to find its reference.
   *
   * @param commit the commit object
   *
   * @return the {@link Issue.Commit} object
   *
   * @throws IOException unable to read from file system
   */
  Issue.Commit read(ObjectId commit) throws IOException;
}
