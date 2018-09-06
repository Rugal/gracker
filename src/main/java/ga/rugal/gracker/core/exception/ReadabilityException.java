package ga.rugal.gracker.core.exception;

import java.io.IOException;

/**
 * Whenever unable to read data from file system.
 *
 * @author Rugal Bernstein
 */
public class ReadabilityException extends IOException {

  public ReadabilityException(final String message) {
    super(message);
  }
}
