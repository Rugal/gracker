package ga.rugal.gracker.core.entity;

import lombok.Value;

/**
 * Issue entity.
 *
 * @author Rugal Bernstein
 */
@Value
public class Issue {

  static enum Status {
    OPEN,
    IN_PROGRESS,
    DONE,
    CLOSE
  }

  /**
   * In commit message.
   */
  private long createTime;

  /**
   * In commit message.
   */
  private long updateTime;

  /**
   * Author.<BR>
   * In commit message.
   */
  private User assigner;

  /**
   * Committer.<BR>
   * In commit message.
   */
  private User assignee;

  /**
   * Title file.
   */
  private String title;

  /**
   * Body file.
   */
  private String body;

  /**
   * In commit message.
   */
  private Status status;

  /**
   * Label file.
   */
  private String[] labels;
}
