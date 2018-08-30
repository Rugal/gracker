package ga.rugal.gracker.shell.runner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ga.rugal.gracker.shell.provider.StringInputProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Non-interactive command line runner.
 *
 * @author Rugal Bernstein
 */
@Component
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 2)
public class NonInteractiveCommandLineRunner implements CommandLineRunner {

  @Autowired
  private ConfigurableEnvironment environment;

  @Autowired
  private Shell shell;

  @Override
  public void run(final String... args) throws Exception {
    final List<String> commandsToRun = Arrays.stream(args)
      .filter(w -> !w.startsWith("@"))
      .collect(Collectors.toList());
    if (!commandsToRun.isEmpty()) {
      InteractiveShellApplicationRunner.disable(this.environment);
      this.shell.run(new StringInputProvider(commandsToRun));
    }
  }
}
