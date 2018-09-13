package ga.rugal.gracker.core.service;

import java.io.IOException;
import java.util.List;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.exception.ReadabilityException;

/**
 * Interface for editor service.
 *
 * @author Rugal Bernstein
 */
public interface EditorService {

  /**
   * Get editor from system by using environment variable.
   *
   * @return editor name
   */
  String getEditor();

  /**
   * Open editor to add issue content.
   *
   * @return issue content object
   *
   * @throws IOException          when unable to access file
   * @throws ReadabilityException when unable to read from file
   * @throws InterruptedException when editor process is interrupted
   */
  Issue.Content createIssueContent() throws InterruptedException,
                                            ReadabilityException,
                                            IOException;

  /**
   * Issue label must be written in CSV format. <BR>
   * Spacing doesn't matter.
   *
   * @return list of label
   *
   * @throws IOException          when unable to access file
   * @throws ReadabilityException when unable to read from file
   * @throws InterruptedException when editor process is interrupted
   */
  List<String> createIssueLabel() throws InterruptedException,
                                         ReadabilityException,
                                         IOException;

  /**
   * Open editor to update issue content.
   *
   * @param label existing label
   *
   * @return new label list
   *
   * @throws IOException          when unable to access file
   * @throws ReadabilityException when unable to read from file
   * @throws InterruptedException when editor process is interrupted
   */
  List<String> updateIssueLabel(List<String> label) throws InterruptedException,
                                                           ReadabilityException,
                                                           IOException;
}
