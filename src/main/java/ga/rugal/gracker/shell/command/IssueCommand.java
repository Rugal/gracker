package ga.rugal.gracker.shell.command;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.exception.ReadabilityException;
import ga.rugal.gracker.core.service.EditorService;
import ga.rugal.gracker.core.service.IssueService;
import ga.rugal.gracker.core.service.TerminalService;

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

  @Resource(name = "list")
  private TerminalService ls;

  @Resource(name = "detail")
  private TerminalService detail;

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

  /**
   * List issue as requested.
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to read from file system
   */
  @ShellMethod("List issues.")
  public String ls() throws IOException {
    final List<Issue> issues = this.issueService.getAllIssue();
    return issues.isEmpty()
           ? "No issue found"
           : this.ls.print(issues);
  }

  /**
   * Show detail of an issue.
   *
   * @param id any format of issue id
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to read from file system
   */
  @ShellMethod("Show issue detail.")
  public String show(final String id) throws IOException {
    final Optional<Issue> issue = this.issueService.get(id);
    return issue.isPresent()
           ? this.detail.print(issue.get())
           : "No issue found with specified id";
  }
}
