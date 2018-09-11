package ga.rugal.gracker.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Utility class for controlling log level.
 *
 * @author Rugal Bernstein
 */
public class LogUtil {

  private static final String PACKAGE = "ga.rugal";

  private LogUtil() {
  }

  /**
   * Set log level.
   *
   * @param level input
   */
  public static void setLogLevel(final String level) {
    Configurator.setLevel(PACKAGE, Level.getLevel(level));
  }
}
