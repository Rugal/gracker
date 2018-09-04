package ga.rugal.gracker.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.service.EditorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation for editor service.
 *
 * @author Rugal Bernstein
 */
@Service
@Slf4j
public class EditorServiceImpl implements EditorService {

  private static final String EDITOR = "EDITOR";

  private static final String GIT_EDITOR = "GIT_EDITOR";

  /**
   * {@inheritDoc}
   */
  @Override
  public String getEditor() {
    if (null != System.getenv(GIT_EDITOR)) {
      final String env = System.getenv(GIT_EDITOR);
      LOG.trace("Using GIT_EDITOR [{}]", env);
      return System.getenv(GIT_EDITOR);
    }

    if (null != System.getenv(EDITOR)) {
      final String env = System.getenv(EDITOR);
      LOG.trace("Using EDITOR [{}]", env);
      return System.getenv(EDITOR);
    }

    LOG.trace("Using DEFAULT EDITOR [{}]", SystemDefaultProperty.DEFAULT_EDITOR);
    return SystemDefaultProperty.DEFAULT_EDITOR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Issue.Content openEditor() throws IOException, InterruptedException {
    final File tempFile = File.createTempFile("gracker_", ".tmp");
    tempFile.deleteOnExit();
    LOG.trace("Write content to temp file [{}]", tempFile.getPath());
    final ProcessBuilder pb = new ProcessBuilder();
    pb.command(this.getEditor(), tempFile.getPath()).inheritIO();
    if (0 != pb.start().waitFor()) {
      throw new IOException("Unable to write issue content to file");
    }
    final Scanner scanner = new Scanner(tempFile, SystemDefaultProperty.ENCODE);
    if (!scanner.hasNext()) {
      throw new IOException("Unable to read issue content from file");
    }
    final String title = scanner.nextLine();
    final StringBuilder body = new StringBuilder();
    while (scanner.hasNext()) {
      body.append(scanner.nextLine());
    }

    return Issue.builder()
      .title(title)
      .body(body.toString())
      .build()
      .getContent();
  }
}
