package ga.rugal.gracker.shell.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class IssueCommand {

  @ShellMethod("Add two integers together.")
  public int add(final int a, final int b) {
    return a + b;
  }
}
