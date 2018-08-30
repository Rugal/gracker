package config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Main entrance class.
 *
 * @author Rugal Bernstein
 */
@SpringBootApplication
public class Main {

  /**
   * Main function.
   *
   * @param args input argument
   *
   * @throws IOException test
   */
  public static void main(final String[] args) throws IOException {
    new SpringApplicationBuilder(Main.class)
      .logStartupInfo(false)
      .run(args);
  }
}
