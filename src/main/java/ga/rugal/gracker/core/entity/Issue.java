package ga.rugal.gracker.core.entity;

import lombok.Value;

/**
 * Issue entity.
 *
 * @author Rugal Bernstein
 */
@Value
public class Issue {

  private long time;

  private String author;

  private String email;
}
