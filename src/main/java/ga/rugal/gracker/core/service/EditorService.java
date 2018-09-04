package ga.rugal.gracker.core.service;

import java.io.IOException;

import ga.rugal.gracker.core.entity.Issue;

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
   * @throws IOException          when unable to access the temp file
   * @throws InterruptedException when editor process is interrupted
   */
  Issue.Content openEditor() throws IOException, InterruptedException;
}
