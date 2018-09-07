package ga.rugal.gracker.core.service.impl;

import java.util.List;
import javax.validation.constraints.NotNull;

import config.SystemDefaultProperty;
import config.TerminalColor;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.service.TerminalService;
import ga.rugal.gracker.util.StringUtil;

import org.springframework.stereotype.Service;

/**
 * Implementation for terminal service.<BR>
 * Responsible for printing colorful content in terminal.
 *
 * @author Rugal Bernstein
 */
@Service
public class TerminalServiceImpl implements TerminalService {

  private static final String LINE_TEMPLATE = "| %s | %s | %s | %s | %s |";

  /**
   * {@inheritDoc}
   */
  @Override
  public String print(final String content, final String color) {
    return String.format("%s%s%s", color, content, TerminalColor.RESET);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String print(final List<Issue> issues) {
    return String.format("%s%n%s", this.header(), this.body(issues));
  }

  private String header() {
    //issue | title | assignee | assigner | status
    return String.format(LINE_TEMPLATE,
                         StringUtil.center(this.print(StringUtil
                           .upperCase(SystemDefaultProperty.ISSUE),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.ISSUE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(SystemDefaultProperty.TITLE),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.TITLE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(SystemDefaultProperty.ASSIGNEE),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.ASSIGNER_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(SystemDefaultProperty.ASSIGNER),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.ASSIGNEE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(SystemDefaultProperty.STATUS),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.STATUS_LENGTH));
  }

  private String body(final @NotNull Issue issue) {
    return String.format(LINE_TEMPLATE,
                         StringUtil.center(this.print(issue.getCommit().getId().getName()
                           .substring(0, SystemDefaultProperty.ISSUE_NUMBER_LENGTH),
                                                      TerminalColor.YELLOW_F),
                                           SystemDefaultProperty.ISSUE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .subString(issue.getContent().getTitle(),
                                      SystemDefaultProperty.TITLE_LENGTH),
                                                      TerminalColor.BLUE_F),
                                           SystemDefaultProperty.TITLE_LENGTH),
                         StringUtil.center(this.print(issue.getCommit().getAssigner().getAuthor(),
                                                      TerminalColor.BLUE_F),
                                           SystemDefaultProperty.ASSIGNER_LENGTH),
                         StringUtil.center(this.print(issue.getCommit().getAssignee().getAuthor(),
                                                      TerminalColor.BLUE_F),
                                           SystemDefaultProperty.ASSIGNEE_LENGTH),
                         StringUtil.center(this.print(issue.getCommit().getStatus().name(),
                                                      TerminalColor.GREEN_F),
                                           SystemDefaultProperty.STATUS_LENGTH));
  }

  private String body(final List<Issue> issues) {
    final StringBuilder sb = new StringBuilder();
    issues.stream().forEach(issue -> sb.append(this.body(issue)).append("\n"));
    return sb.toString();
  }
}
