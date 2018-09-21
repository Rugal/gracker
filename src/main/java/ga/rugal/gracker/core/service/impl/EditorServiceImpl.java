package ga.rugal.gracker.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    LOG.trace("Get editor");
    if (null != System.getenv(GIT_EDITOR)) {
      final String env = System.getenv(GIT_EDITOR);
      LOG.debug("Using GIT_EDITOR [{}]", env);
      return System.getenv(GIT_EDITOR);
    }

    if (null != System.getenv(EDITOR)) {
      final String env = System.getenv(EDITOR);
      LOG.debug("Using EDITOR [{}]", env);
      return System.getenv(EDITOR);
    }

    LOG.debug("Using DEFAULT EDITOR [{}]", SystemDefaultProperty.DEFAULT_EDITOR);
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

      LOG.trace("Build issue content");
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
    LOG.trace("Create issue label");
    return this.updateIssueLabel(null);
  }

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
  @Override
  public List<String> updateIssueLabel(final List<String> label) throws InterruptedException,
                                                                        ReadabilityException,
                                                                        IOException {
    final File tempFile = File.createTempFile("gracker_", ".tmp");
    if (null != label) {
      LOG.trace("Write template to temporary file");
      this.writeTemplate(tempFile, String.join(",", label));
    }
    this.openFile(tempFile);
    try (Scanner scanner = new Scanner(tempFile, SystemDefaultProperty.ENCODE)) {
      if (!scanner.hasNext()) {
        throw new ReadabilityException("No label found");
      }
      LOG.trace("Build issue label");
      return Arrays.asList(scanner.nextLine().split(",")).stream()
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .distinct()
        .collect(Collectors.toList());
    }
  }

  /**
   * Write template content to file before user can view it.
   *
   * @param tempFile the target file
   * @param content  the content to write
   *
   * @throws IOException unable to write to file system
   */
  private void writeTemplate(final File tempFile, final String content) throws IOException {
    try (PrintWriter writer = new PrintWriter(tempFile, SystemDefaultProperty.ENCODE)) {
      writer.print(content);
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
    LOG.debug("Open temp file [{}]", tempFile.getPath());
    final ProcessBuilder pb = new ProcessBuilder();
    pb.command(this.getEditor(), tempFile.getPath()).inheritIO();
    if (0 != pb.start().waitFor()) {
      LOG.error("Editor process error out");
      throw new IOException("Unable to write issue content to file");
    }
    return tempFile;
  }
}
