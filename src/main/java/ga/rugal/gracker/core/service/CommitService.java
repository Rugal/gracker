package ga.rugal.gracker.core.service;

import java.io.IOException;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;

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
   * Read and parse commit object from repository.
   *
   * @param commit the commit object
   *
   * @return the {@link Issue.Commit} object
   *
   * @throws IOException unable to read from file system
   */
  Issue.Commit read(ObjectId commit) throws IOException;
}
