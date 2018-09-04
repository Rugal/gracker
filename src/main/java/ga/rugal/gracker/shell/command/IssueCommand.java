package ga.rugal.gracker.shell.command;

import java.io.File;
import java.io.IOException;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.entity.Status;
import ga.rugal.gracker.core.service.IssueService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Slf4j
public class IssueCommand {

  private static final String EDITOR = "EDITOR";

  private static final String GIT_EDITOR = "GIT_EDITOR";

  @Autowired
  private IssueService issueService;

  private boolean useEditor(final String title, final String content) {
    return title.equals(SystemDefaultProperty.NULL)
           || content.equals(SystemDefaultProperty.NULL);
  }

  /**
   * Get editor from system by using environment variable.
   *
   * @return editor name
   */
  private String getEditor() {
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
   * Create an issue.
   *
   * @param title issue title
   * @param body  issue body
   *
   * @return
   *
   * @throws IOException          test
   * @throws InterruptedException test
   */
  @ShellMethod("Create issue.")
  public String create(final @ShellOption(defaultValue = SystemDefaultProperty.NULL) String title,
                       final @ShellOption(defaultValue = SystemDefaultProperty.NULL) String body)
    throws IOException, InterruptedException {

    final Issue issue = new Issue();

    if (this.useEditor(title, body)) {
      final File tempFile = File.createTempFile("gracker_", ".tmp");
      tempFile.deleteOnExit();
      LOG.trace("Write content to temp file [{}]", tempFile.getPath());
      final ProcessBuilder pb = new ProcessBuilder();
      pb.command(this.getEditor(), tempFile.getPath()).inheritIO();
      return "test";
    }
    //create issue right away with the content that uses provides
    final Issue.Content content = new Issue.Content();
    content.setBody(body);
    content.setTitle(title);
    issue.setContent(content);
    final Issue.Commit commit = new Issue.Commit();
    commit.setStatus(Status.OPEN);
    issue.setCommit(commit);

    final RawIssue rawIssue = this.issueService.create(issue);
    return rawIssue.getCommit().getName();
  }

  @ShellMethod("List issues.")
  public int ls() {
    return 0;
  }

  @ShellMethod("Show issue detail.")
  public int show(final String id) {
    return 0;
  }
}
