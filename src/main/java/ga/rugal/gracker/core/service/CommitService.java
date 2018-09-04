package ga.rugal.gracker.core.service;

import java.io.IOException;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;

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
}
