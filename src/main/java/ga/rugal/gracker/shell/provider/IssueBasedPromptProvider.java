package ga.rugal.gracker.shell.provider;

import org.eclipse.jgit.lib.ObjectId;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * Change prompt based on current issue.
 *
 * @author Rugal Bernstein
 */
@Component
public class IssueBasedPromptProvider implements PromptProvider {

  private ObjectId id = null;

  @Override
  public AttributedString getPrompt() {
    return new AttributedString(this.id == null ? ":>" : String.format("%s:>", this.id.getName()),
                                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
  }

  @EventListener
  public void handle(final IssueEvent e) {
    this.id = e.getId();
  }
}
