package ga.rugal.gracker.shell.command;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import config.Constant;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.Status;
import ga.rugal.gracker.core.exception.IssueNotFoundException;
import ga.rugal.gracker.core.service.IssueService;
import ga.rugal.gracker.shell.provider.IssueBasedPromptProvider;
import ga.rugal.gracker.util.LogUtil;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Status and network related commands.
 *
 * @author Rugal Bernstein
 */
@ShellComponent
@Slf4j
public class TransmissionCommand {

  @Autowired
  private IssueBasedPromptProvider issueBasedPromptProvider;

  @Autowired
  private IssueService issueService;

  /**
   * Use this method to choose whether use the id that input just now from command line or use the
   * one that store in the prompt provider.<BR>
   * 1. Use commandLineId if it exists<BR>
   * 2. Use prompt id if it exists<BR>
   * 3. Return empty object else
   *
   * @param commandLineId the id get just from command line
   *
   * @return final id
   */
  private Optional<String> getCurrentId(final String commandLineId) {
    if (!Constant.NULL.equals(commandLineId)) {
      LOG.trace("");
      return Optional.of(commandLineId);
    }
    final ObjectId objectId = this.issueBasedPromptProvider.getId();
    return Objects.isNull(objectId)
           ? Optional.empty()
           : Optional.of(objectId.getName());

  }

  @ShellMethod("Download issue information.")
  public int fetch(final int a, final int b) {
    return a + b;
  }

  @ShellMethod("Download and combine issue into current reference.")
  public int pull() {
    return 0;
  }

  @ShellMethod("Upload local issue to remote.")
  public int push(final String id) {
    return 0;
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

    LogUtil.setLogLevel(level);
    final Optional<String> currentId = this.getCurrentId(id);
    if (!currentId.isPresent()) {
      return Constant.NO_ID_ENTER;
    }

    final Optional<Issue> optional = this.issueService.get(currentId.get());
    if (!optional.isPresent()) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    final Issue issue = optional.get();
    if (!Status.OPEN.equals(issue.getCommit().getStatus())) {
      return String.format("Issue status must be %s", Status.OPEN);
    }

    issue.getCommit().setStatus(Status.IN_PROGRESS);
    try {
      this.issueService.update(issue);
    } catch (final IssueNotFoundException ex) {
      return Constant.NO_ISSUE_FOR_ID;
    }

    return String.format("Start issue [%s]", id);
  }

  @ShellMethod("IN_PROGRESS => DONE")
  public void finish() {

  }

  @ShellMethod("DONE => CLOSE")
  public void resolve() {

  }

  @ShellMethod("DONE => OPEN")
  public void rework() {

  }

  @ShellMethod("CLOSE => OPEN")
  public void reopen() {

  }
}
