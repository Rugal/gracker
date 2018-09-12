package ga.rugal.gracker.shell.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TransmissionCommand {

  @ShellMethod("Download issue information.")
  public int fetch(final int a, final int b) {
    return a + b;
  }

  @ShellMethod("Download and combine issue into current reference.")
  public int pull() {
    return 0;
  }

  @ShellMethod("Upload local issue to remote.")
  public int push(final String id) {
    return 0;
  }

  @ShellMethod("OPEN => IN_PROGRESS")
  public void start() {

  }

  @ShellMethod("IN_PROGRESS => DONE")
  public void finish() {

  }

  @ShellMethod("DONE => CLOSE")
  public void resolve() {

  }

  @ShellMethod("DONE => OPEN")
  public void rework() {

  }

  @ShellMethod("CLOSE => OPEN")
  public void reopen() {

  }
}
