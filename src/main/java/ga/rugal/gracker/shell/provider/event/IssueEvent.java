package ga.rugal.gracker.shell.provider.event;

import lombok.Getter;
import org.eclipse.jgit.lib.ObjectId;
import org.springframework.context.ApplicationEvent;

/**
 * Contains the current issue object.
 *
 * @author Rugal Bernstein
 */
public class IssueEvent extends ApplicationEvent {

  private static final long serialVersionUID = 7099057708183571917L;

  @Getter
  private final ObjectId id;

  public IssueEvent(final Object source, final ObjectId id) {
    super(source);
    this.id = id;
  }
}
