package ga.rugal.gracker.shell.provider;

import java.util.Objects;

import config.SystemDefaultProperty;

import ga.rugal.gracker.shell.provider.event.IssueEvent;

import lombok.Getter;
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

  @Getter
  private ObjectId id = null;

  private String get(final ObjectId id) {
    return String.format("%s:>", Objects.isNull(id)
                                 ? "issue"
                                 : id.getName()
                           .substring(0, SystemDefaultProperty.ISSUE_NUMBER_LENGTH));
  }

  @Override
  public AttributedString getPrompt() {
    return new AttributedString(this.get(this.id),
                                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
  }

  @EventListener
  public void handle(final IssueEvent e) {
    this.id = e.getId();
  }
}
