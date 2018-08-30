package ga.rugal.gracker.shell.command;

import java.io.IOException;

import config.SystemDefaultProperty;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class IssueCommand {

  private boolean useEditor(final String title, final String content) {
    return title.equals(SystemDefaultProperty.NULL)
           || content.equals(SystemDefaultProperty.NULL);
  }

  /**
   * Create an issue.
   *
   * @param title   issue title
   * @param content issue message
   *
   * @return
   *
   * @throws IOException          test
   * @throws InterruptedException test
   */
  @ShellMethod("Create issue.")
  public int create(final @ShellOption(defaultValue = SystemDefaultProperty.NULL) String title,
                    final @ShellOption(defaultValue = SystemDefaultProperty.NULL) String content)
    throws IOException, InterruptedException {

    if (!this.useEditor(title, content)) {
      //create issue right away with the content that uses provides
      return 1;
    }

    final ProcessBuilder pb = new ProcessBuilder();
    pb.command("vim", "pom.xml").inheritIO();
    return pb.start().waitFor();
  }

  @ShellMethod("List issues.")
  public int ls() {
    return 0;
  }

  @ShellMethod("Show issue detail.")
  public int show(final String id) {
    return 0;
  }
}
