package ga.rugal.gracker.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.exception.ReadabilityException;
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
  public Issue.Content createIssueContent() throws InterruptedException,
                                                   ReadabilityException,
                                                   IOException {
    final File tempFile = File.createTempFile("gracker_", ".tmp");
    this.openFile(tempFile);
    try (Scanner scanner = new Scanner(tempFile, SystemDefaultProperty.ENCODE)) {
      if (!scanner.hasNext()) {
        throw new ReadabilityException("Issue title not found");
      }
      final String title = scanner.nextLine();
      final StringBuilder body = new StringBuilder();
      if (!scanner.hasNext()) {
        throw new ReadabilityException("Issue body not found");
      }
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

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> createIssueLabel() throws InterruptedException,
                                                ReadabilityException,
                                                IOException {
    final File tempFile = File.createTempFile("gracker_", ".tmp");
    this.openFile(tempFile);
    try (Scanner scanner = new Scanner(tempFile, SystemDefaultProperty.ENCODE)) {
      if (!scanner.hasNext()) {
        throw new ReadabilityException("No label found");
      }
      return Arrays.asList(scanner.nextLine().split(",")).stream()
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList());
    }
  }

  /**
   * Open file by editor and let user to write in a temp file.
   *
   * @param tempFile the temporary file to write with
   *
   * @return the finished temp file
   *
   * @throws IOException          Unable to write to temporary file
   * @throws InterruptedException editor process interrupted
   */
  private File openFile(final File tempFile) throws IOException, InterruptedException {
    tempFile.deleteOnExit();
    LOG.trace("Open temp file [{}]", tempFile.getPath());
    final ProcessBuilder pb = new ProcessBuilder();
    pb.command(this.getEditor(), tempFile.getPath()).inheritIO();
    if (0 != pb.start().waitFor()) {
      throw new IOException("Unable to write issue content to file");
    }
    return tempFile;
  }
}
