package ga.rugal.gracker.core.service.impl;

import java.util.Objects;

import config.Constant;
import config.SystemDefaultProperty;
import config.TerminalColor;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.Issue.User;
import ga.rugal.gracker.core.service.TerminalService;
import ga.rugal.gracker.util.DateUtil;
import ga.rugal.gracker.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation for terminal service.<BR>
 * Responsible for printing colorful content in terminal.
 *
 * @author Rugal Bernstein
 */
@Service("detail")
@Slf4j
public class DetailTerminalServiceImpl implements TerminalService<Issue> {

  /**
   * Print issue detail in following format.<BR>
   * <BR>
   * id: id of issue status: issue status<BR>
   * title: issue title<BR>
   * <BR>
   * author: author name createdAt: creation time<BR>
   * committer: committer name updatedAT: updation time<BR>
   * <BR>
   * label: X, X, X, X<BR>
   * <BR>
   * Description: <BR>
   * XXXXXX
   *
   *
   * @param issue input data
   *
   * @return content to display
   */
  @Override
  public String print(final Issue issue) {
    return String.format("%s%n%s%n%n%s%n%s%n%n%s%n%n%s%n",
                         this.line1(issue),
                         String.format("%s: %-50s",
                                       StringUtil.upperCase(Constant.TITLE),
                                       issue.getContent().getTitle()),
                         this.author(issue.getCommit().getAssigner(),
                                     Constant.ASSIGNER,
                                     Constant.CREATE),
                         this.author(issue.getCommit().getAssignee(),
                                     Constant.ASSIGNEE,
                                     Constant.UPDATE),
                         this.label(issue),
                         String.format("%s:%n%s",
                                       StringUtil.upperCase(Constant.BODY),
                                       issue.getContent().getBody()));
  }

  private String label(final Issue issue) {
    LOG.trace("Start building issue label");
    return String.format("%s: %s",
                         StringUtil.upperCase(Constant.LABEL),
                         Objects.isNull(issue.getContent().getLabel())
                         ? "NONE"
                         : this.print(String.join(",", issue.getContent().getLabel()),
                                      TerminalColor.CYAN_F));
  }

  private String line1(final Issue issue) {
    final String id = issue.getCommit().getId().getName()
      .substring(0, SystemDefaultProperty.ISSUE_NUMBER_LENGTH);
    return String.format("%s: %-20s %s: %-20s",
                         StringUtil.upperCase(Constant.ISSUE),
                         this.print(id, TerminalColor.YELLOW_F),
                         StringUtil.upperCase(Constant.STATUS),
                         this.print(issue.getCommit().getStatus().name(), TerminalColor.GREEN_F));
  }

  private String author(final User user, final String authorLabel, final String timeLabel) {
    LOG.trace("Start building issue author");
    return String.format("%s: %-20s %s: %-30s",
                         StringUtil.upperCase(authorLabel),
                         this.print(user.getAuthor(), TerminalColor.BLUE_F),
                         StringUtil.upperCase(timeLabel),
                         this.print(DateUtil.getFullString(user.getTime()), TerminalColor.RED_F));
  }
}
