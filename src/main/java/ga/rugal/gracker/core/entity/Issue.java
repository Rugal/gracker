package ga.rugal.gracker.core.entity;

import java.util.List;

import lombok.Data;

/**
 * Issue entity.
 *
 * @author Rugal Bernstein
 */
@Data
public class Issue {

  private Commit commit;

  private Content content;

  @Data
  public static class Commit {

    /**
     * Author.<BR>
     * In commit tree.
     */
    private User assigner;

    /**
     * Committer.<BR>
     * In commit tree.
     */
    private User assignee;

    /**
     * In commit message.
     */
    private Status status;
  }

  @Data
  public static class Content {

    /**
     * Title file.
     */
    private String title;

    /**
     * Body file.
     */
    private String body;

    /**
     * Label file.
     */
    private List<String> label;
  }

  @Data
  public static class User {

    /**
     * Author name.<BR>
     * In commit tree.
     */
    private String author;

    /**
     * Author email.<BR>
     * In commit tree.
     */
    private String email;

    /**
     * Along with author.<BR>
     * In commit tree.
     */
    private long time;
  }
}
