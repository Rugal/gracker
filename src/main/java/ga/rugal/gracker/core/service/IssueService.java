package ga.rugal.gracker.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.exception.IssueNotFoundException;

import org.eclipse.jgit.revwalk.RevCommit;

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
   * Update an issue and set the relevant reference points to the new commit object.<BR>
   *
   * @param issue new issue object
   *
   * @return new raw issue
   *
   * @throws IOException            unable to write to file system
   * @throws IssueNotFoundException parrent issue not found
   */
  RawIssue update(Issue issue) throws IOException, IssueNotFoundException;

  /**
   * Get all issues from repository.
   *
   * @return list of issue
   *
   * @throws IOException unable to read from file system
   */
  List<Issue> getAllIssue() throws IOException;

  /**
   * Get one issue by issue id.
   *
   * @param id issue id
   *
   * @return assembled issue object
   *
   * @throws IOException unable to read from file system
   */
  Optional<Issue> get(String id) throws IOException;

  /**
   * Get one issue by revision commit object.<BR>
   * Basically just assemble issue object itself.
   *
   * @param revCommit revision commit object
   *
   * @return assembled issue object
   *
   * @throws IOException unable to read from file system
   */
  Issue get(RevCommit revCommit) throws IOException;

  /**
   * Use this method to choose whether use the id that input just now from command line or use the
   * one that store in the prompt provider.<BR>
   * 1. Use commandLineId if it exists<BR>
   * 2. Use prompt id if it exists<BR>
   * 3. Return empty object else
   *
   * @param commandLineId the id get just from command line
   *
   * @return final id
   */
  Optional<String> getCurrentId(String commandLineId);
}
