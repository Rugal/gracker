package ga.rugal.gracker.shell.provider;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Input;
import org.springframework.shell.InputProvider;
import org.springframework.util.StringUtils;

/**
 * Format string input.
 *
 * @author Rugal Bernstein
 */
@RequiredArgsConstructor
public class StringInputProvider implements InputProvider {

  private final List<String> words;

  private boolean done;

  @Override
  public Input readInput() {
    if (this.done) {
      return null;
    }
    this.done = true;
    return new Input() {
      @Override
      public List<String> words() {
        return StringInputProvider.this.words;
      }

      @Override
      public String rawText() {
        return StringUtils.collectionToDelimitedString(StringInputProvider.this.words, " ");
      }
    };
  }
}
