package ga.rugal.gracker.shell.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TransmissionCommand {

  @ShellMethod("Download issue information.")
  public int fetch(final int a, final int b) {
    return a + b;
  }

  @ShellMethod("Download and combine issue in current reference.")
  public int pull() {
    return 0;
  }

  @ShellMethod("Upload local issue to remote.")
  public int push(final String id) {
    return 0;
  }
}
