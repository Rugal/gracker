package ga.rugal.gracker.core.service;

import java.io.IOException;
import java.util.List;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;

/**
 * interface for issue service.
 *
 * @author Rugal Bernstein
 */
public interface IssueService {

  /**
   * Create brand new issue along with reference.
   *
   * @param issue issue content
   *
   * @return the created raw issue object
   *
   * @throws IOException unable to write to file system
   */
  RawIssue create(Issue issue) throws IOException;

  /**
   * Get all issues from repository.
   *
   * @return list of issue
   *
   * @throws IOException unable to read from file system
   */
  List<Issue> getAllIssue() throws IOException;
}
