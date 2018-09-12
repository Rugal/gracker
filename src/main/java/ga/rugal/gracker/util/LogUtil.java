package ga.rugal.gracker.util;

import config.Constant;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Utility class for controlling log level.
 *
 * @author Rugal Bernstein
 */
public final class LogUtil {

  private static final String PACKAGE = ga.rugal.PackageInfo.class.getPackage().getName();

  private LogUtil() {
  }

  /**
   * Set log level.
   *
   * @param level input
   */
  public static void setLogLevel(final String level) {
    switch (level) {
      case Constant.TRACE:
      case Constant.DEBUG:
        Configurator.setLevel(PACKAGE, Level.getLevel(level));
        break;
      default:
        Configurator.setLevel(PACKAGE, Level.ERROR);
    }
  }
}
