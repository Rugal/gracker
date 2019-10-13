package ga.rugal.gracker.core.entity;

import lombok.Data;
import org.eclipse.jgit.lib.ObjectId;

/**
 * RawIssue entity. Contains unparsed git object information.
 *
 * @author Rugal Bernstein
 */
@Data
public class RawIssue {

  private ObjectId tree;

  private ObjectId commit;

  private Content content;

  @Data
  public static class Content {

    private ObjectId title;

    private ObjectId body;

    private ObjectId label;
  }
}
