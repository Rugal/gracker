package ga.rugal.gracker.shell.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;

import config.Constant;
import config.SystemDefaultProperty;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.entity.Status;
import ga.rugal.gracker.core.exception.IssueNotFoundException;
import ga.rugal.gracker.core.exception.ReadabilityException;
import ga.rugal.gracker.core.service.EditorService;
import ga.rugal.gracker.core.service.IssueService;
import ga.rugal.gracker.core.service.TerminalService;
import ga.rugal.gracker.shell.provider.event.IssueEvent;
import ga.rugal.gracker.util.CollectionUtil;
import ga.rugal.gracker.util.LogUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Issue content related commands.
 *
 * @author Rugal Bernstein
 */
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

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  /**
   * See if need to use editor.
   *
   * @param title issue title
   * @param body  issue body
   *
   * @return true if necessary, otherwise false
   */
  private boolean useEditor(final String title, final String body) {
    return title.equals(Constant.NULL)
           || body.equals(Constant.NULL);
  }

  /**
   * Transit status of issue from a status to another one.
   *
   * @param id    issue id
   * @param from  current status
   * @param to    next status
   * @param level log level
   *
   * @return content to display
   *
   * @throws IOException unable to write to file system
   */
  private String transit(final String id, final Status from, final Status to, final String level)
    throws IOException {

    LogUtil.setLogLevel(level);
    final Optional<String> currentId = this.issueService.getCurrentId(id);
    if (!currentId.isPresent()) {
      return Constant.NO_ID_ENTER;
    }

    final Optional<Issue> optional = this.issueService.get(currentId.get());
    if (!optional.isPresent()) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    final Issue issue = optional.get();
    if (!from.equals(issue.getCommit().getStatus())) {
      return String.format("Issue status must be [%s]", from);
    }

    issue.getCommit().setStatus(to);
    try {
      this.issueService.update(issue);
    } catch (final IssueNotFoundException ex) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    return String.format("Issue [%s] status is [%s]", id, to);
  }

  /**
   * Use an issue as current editing one, so the subsequent operations will be done against this
   * issue.
   *
   * @param id    issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to read from file system
   */
  @ShellMethod("Use issue.")
  public String use(final @ShellOption(help = Constant.ANY_FORMAT) String id,
                    final @ShellOption(defaultValue = Constant.ERROR,
                                       help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);
    final Optional<Issue> optional = this.issueService.get(id);
    if (!optional.isPresent()) {
      return Constant.NO_ISSUE_FOR_ID;
    }
    final IssueEvent event = new IssueEvent(this, optional.get().getCommit().getId());
    this.applicationEventPublisher.publishEvent(event);
    return String.format("Set current issue as [%s]",
                         id.substring(0, SystemDefaultProperty.ISSUE_NUMBER_LENGTH));
  }

  /**
   * Create an issue.<BR>
   * User can either create issue directly through command line parameters, or by editing a temp
   * file.<BR>
   *
   * @param title issue title
   * @param body  issue body
   * @param level log level
   *
   * @return Issue creation status
   *
   * @throws IOException          when unable to access file system
   * @throws InterruptedException when editor process is interrupted
   */
  @ShellMethod("Create issue.")
  public String create(final @ShellOption(defaultValue = Constant.NULL) String title,
                       final @ShellOption(defaultValue = Constant.NULL) String body,
                       final @ShellOption(defaultValue = Constant.ERROR,
                                          help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException, InterruptedException {

    LogUtil.setLogLevel(level);
    final Issue issue;
    try {
      final Issue.Content content = this.useEditor(title, body)
                                    ? this.editorService.createIssueContent()
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
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to read from file system
   */
  @ShellMethod("List issues.")
  public String ls(final @ShellOption(defaultValue = Constant.ERROR,
                                      help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);
    final List<Issue> issues = this.issueService.getAllIssue();
    return issues.isEmpty()
           ? Constant.NO_ISSUE
           : this.ls.print(issues);
  }

  /**
   * Show detail of an issue.
   *
   * @param id    any format of issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to read from file system
   */
  @ShellMethod("Show issue detail.")
  public String show(final @ShellOption(defaultValue = Constant.NULL,
                                        help = Constant.ANY_FORMAT) String id,
                     final @ShellOption(defaultValue = Constant.ERROR,
                                        help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);
    final Optional<String> currentId = this.issueService.getCurrentId(id);
    if (!currentId.isPresent()) {
      return Constant.NO_ID_ENTER;
    }

    final Optional<Issue> optional = this.issueService.get(currentId.get());
    return optional.isPresent()
           ? this.detail.print(optional.get())
           : Constant.NO_ISSUE_FOR_ID;
  }

  /**
   * Assign issue to user.
   *
   * @param id    issue id
   * @param name  assignee name
   * @param email assignee email
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("Assign issue to user.")
  public String assign(final @ShellOption(defaultValue = Constant.NULL,
                                          help = Constant.ANY_FORMAT) String id,
                       final @ShellOption(help = "Assignee name") String name,
                       final @ShellOption(help = "Assignee email") String email,
                       final @ShellOption(defaultValue = Constant.ERROR,
                                          help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);
    final Optional<String> currentId = this.issueService.getCurrentId(id);
    if (!currentId.isPresent()) {
      return Constant.NO_ID_ENTER;
    }

    final Optional<Issue> optional = this.issueService.get(currentId.get());
    if (!optional.isPresent()) {
      return Constant.NO_ISSUE_FOR_ID;
    }
    final Issue issue = optional.get();
    issue.getCommit().setAssignee(new Issue.User(name, email));
    try {
      this.issueService.update(issue);
    } catch (final IssueNotFoundException ex) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    return String.format("Assign issue [%s] to [%s]", currentId.get(), name);
  }

  /**
   * Label an issue.
   *
   * @param id    any format of issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws InterruptedException editor process interrupted
   * @throws IOException          unable to write to file system
   */
  @ShellMethod("Update issue label.")
  public String label(final @ShellOption(defaultValue = Constant.NULL,
                                         help = Constant.ANY_FORMAT) String id,
                      final @ShellOption(defaultValue = Constant.ERROR,
                                         help = Constant.AVAILABLE_LEVEL) String level)
    throws InterruptedException, IOException {

    LogUtil.setLogLevel(level);
    final Optional<String> currentId = this.issueService.getCurrentId(id);
    if (!currentId.isPresent()) {
      return Constant.NO_ID_ENTER;
    }

    final Optional<Issue> optional = this.issueService.get(currentId.get());
    if (!optional.isPresent()) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    final Issue issue = optional.get();
    final List<String> labels;
    try {
      labels = this.editorService.updateIssueLabel(issue.getContent().getLabel());
      Collections.sort(labels);
    } catch (final ReadabilityException e) {
      return e.getMessage();
    }

    LOG.trace("Issue [{}] use to have [{}] label(s)",
              issue.getCommit().getId().getName(),
              issue.getContent().getLabel() == null
              ? 0
              : issue.getContent().getLabel().size());

    if (CollectionUtil.equals(labels, issue.getContent().getLabel())) {
      return "No label change detected";
    }

    issue.getContent().setLabel(labels);
    try {
      this.issueService.update(issue);
      LOG.trace("Issue [{}] now has [{}] label(s)",
                issue.getCommit().getId().getName(),
                issue.getContent().getLabel() == null
                ? 0
                : issue.getContent().getLabel().size());
    } catch (final IssueNotFoundException ex) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    return String.format("Label issue [%s] complete", issue.getCommit().getId().getName());
  }

  /**
   * Transit from OPEN to IN_PROGRESS.
   *
   * @param id    issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("OPEN => IN_PROGRESS")
  public String start(final @ShellOption(defaultValue = Constant.NULL,
                                         help = Constant.ANY_FORMAT) String id,
                      final @ShellOption(defaultValue = Constant.ERROR,
                                         help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    return this.transit(id, Status.OPEN, Status.IN_PROGRESS, level);
  }

  /**
   * Transit from IN_PROGRESS to DONE.
   *
   * @param id    issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("IN_PROGRESS => DONE")
  public String finish(final @ShellOption(defaultValue = Constant.NULL,
                                          help = Constant.ANY_FORMAT) String id,
                       final @ShellOption(defaultValue = Constant.ERROR,
                                          help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    return this.transit(id, Status.IN_PROGRESS, Status.DONE, level);
  }

  /**
   * Transit from DONE to CLOSE.
   *
   * @param id    issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("DONE => CLOSE")
  public String resolve(final @ShellOption(defaultValue = Constant.NULL,
                                           help = Constant.ANY_FORMAT) String id,
                        final @ShellOption(defaultValue = Constant.ERROR,
                                           help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    return this.transit(id, Status.DONE, Status.CLOSE, level);
  }

  /**
   * Transit from DONE to OPEN.
   *
   * @param id    issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("DONE => OPEN")
  public String rework(final @ShellOption(defaultValue = Constant.NULL,
                                          help = Constant.ANY_FORMAT) String id,
                       final @ShellOption(defaultValue = Constant.ERROR,
                                          help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    return this.transit(id, Status.DONE, Status.OPEN, level);
  }

  /**
   * Transit from CLOSE to OPEN.
   *
   * @param id    issue id
   * @param level log level
   *
   * @return the content to be displayed
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("CLOSE => OPEN")
  public String reopen(final @ShellOption(defaultValue = Constant.NULL,
                                          help = Constant.ANY_FORMAT) String id,
                       final @ShellOption(defaultValue = Constant.ERROR,
                                          help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    return this.transit(id, Status.CLOSE, Status.OPEN, level);
  }
}
