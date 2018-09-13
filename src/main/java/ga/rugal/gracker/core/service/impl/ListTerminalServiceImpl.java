package ga.rugal.gracker.core.service.impl;

import java.util.List;
import javax.validation.constraints.NotNull;

import config.Constant;
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
@Service("list")
public class ListTerminalServiceImpl implements TerminalService<List<Issue>> {

  private static final String LINE_TEMPLATE = "| %s | %s | %s | %s | %s |";

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
                           .upperCase(Constant.ISSUE),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.ISSUE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(Constant.TITLE),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.TITLE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(Constant.ASSIGNER),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.ASSIGNER_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(Constant.ASSIGNEE),
                                                      TerminalColor.WHITE_F),
                                           SystemDefaultProperty.ASSIGNEE_LENGTH),
                         StringUtil.center(this.print(StringUtil
                           .upperCase(Constant.STATUS),
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
                                                      TerminalColor.RED_F),
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
