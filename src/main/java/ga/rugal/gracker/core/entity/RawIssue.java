package ga.rugal.gracker.core.entity;

import lombok.Data;
import org.eclipse.jgit.lib.ObjectId;

/**
 * RawIssue entity.
 *
 * @author Rugal Bernstein
 */
@Data
public class RawIssue {

  private ObjectId tree;

  private ObjectId commit;

  @Data
  public static class Content {

    private ObjectId title;

    private ObjectId body;

    private ObjectId label;
  }
}
