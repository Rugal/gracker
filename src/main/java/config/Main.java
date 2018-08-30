package config;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    SpringApplication.run(Main.class, args);
  }
}
