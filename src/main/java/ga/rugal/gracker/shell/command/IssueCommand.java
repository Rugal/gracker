package ga.rugal.gracker.shell.command;

import java.io.IOException;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.service.EditorService;
import ga.rugal.gracker.core.service.IssueService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Slf4j
public class IssueCommand {

  @Autowired
  private EditorService editorService;

  @Autowired
  private IssueService issueService;

  private boolean useEditor(final String title, final String content) {
    return title.equals(SystemDefaultProperty.NULL)
           || content.equals(SystemDefaultProperty.NULL);
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
    final Issue.Content content = this.useEditor(title, body)
                                  ? this.editorService.openEditor()
                                  : Issue.builder().title(title).body(body).build().getContent();
    final Issue issue = Issue.builder()
      .content(content)
      .build();

    final RawIssue rawIssue = this.issueService.create(issue);
    return String.format("New issue created [%s]",
                         rawIssue.getCommit().getName()
                           .substring(0, SystemDefaultProperty.ISSUE_NUMBER_LENGTH));
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
