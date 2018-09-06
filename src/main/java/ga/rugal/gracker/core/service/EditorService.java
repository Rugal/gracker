package ga.rugal.gracker.core.service;

import java.io.IOException;

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
  Issue.Content openEditor() throws InterruptedException,
                                    ReadabilityException,
                                    IOException;
}
