package ga.rugal.gracker.shell.command;

import java.io.IOException;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.exception.ReadabilityException;
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
   * Create an issue.<BR>
   * User can either create issue directly through command line parameters, or by editing a temp
   * file.<BR>
   *
   * @param title issue title
   * @param body  issue body
   *
   * @return Issue creation status
   *
   * @throws IOException          when unable to access file system
   * @throws InterruptedException when editor process is interrupted
   */
  @ShellMethod("Create issue.")
  public String create(final @ShellOption(defaultValue = SystemDefaultProperty.NULL) String title,
                       final @ShellOption(defaultValue = SystemDefaultProperty.NULL) String body)
    throws IOException, InterruptedException {

    final Issue issue;
    try {
      final Issue.Content content = this.useEditor(title, body)
                                    ? this.editorService.openEditor()
                                    : Issue.builder().title(title).body(body).build().getContent();
      issue = Issue.builder()
        .content(content)
        .build();
    } catch (final ReadabilityException e) {
      //Means user didn't fill the content
      return e.getMessage();
    }

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
