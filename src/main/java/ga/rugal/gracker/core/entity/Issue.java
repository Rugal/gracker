package ga.rugal.gracker.core.entity;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Issue entity.
 *
 * @author Rugal Bernstein
 */
@Data
@Slf4j
public class Issue {

  public static IssueBuilder builder() {
    return new IssueBuilder();
  }

  private Commit commit;

  private Content content;

  public static class IssueBuilder {

    private final Issue issue = new Issue();

    public IssueBuilder() {
      this.issue.setCommit(new Commit());
      this.issue.setContent(new Content());
    }

    public IssueBuilder title(final String title) {
      this.issue.getContent().setTitle(title);
      return this;
    }

    public IssueBuilder body(final String body) {
      this.issue.getContent().setBody(body);
      return this;
    }

    public IssueBuilder label(final List<String> label) {
      this.issue.getContent().setLabel(label);
      return this;
    }

    public IssueBuilder status(final Status status) {
      this.issue.getCommit().setStatus(status);
      return this;
    }

    public IssueBuilder assigner(final User assigner) {
      this.issue.getCommit().setAssigner(assigner);
      return this;
    }

    public IssueBuilder assignee(final User assignee) {
      this.issue.getCommit().setAssignee(assignee);
      return this;
    }

    public IssueBuilder commit(final @NotNull Commit commit) {
      this.issue.setCommit(commit);
      return this;
    }

    public IssueBuilder content(final @NotNull Content content) {
      this.issue.setContent(content);
      return this;
    }

    public Issue build() {
      return this.issue;
    }
  }

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
    private Status status = Status.OPEN;
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

    /**
     * Set field by key.
     *
     * @param key   field name
     * @param value content
     */
    public void set(final String key, final String value) {
      try {
        final Field field = Content.class.getField(key);
        field.set(this, value);
      } catch (final NoSuchFieldException ex) {
        LOG.warn("key [{}] not found", key);
      } catch (final IllegalArgumentException | IllegalAccessException | SecurityException ex) {
        LOG.error("Unable to access key [{}]", key);
      }
    }
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
    private long time = Instant.now().getEpochSecond();

    public User() {
    }

    public User(final String author, final String email) {
      this.author = author;
      this.email = email;
    }
  }

}
