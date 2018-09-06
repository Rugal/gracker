package ga.rugal.gracker.core.service;

/**
 * Interface for terminal service.
 *
 * @author Rugal Bernstein
 */
public interface TerminalService {

  /**
   * Print content to terminal with color.
   *
   * @param content the content to be printed
   * @param color   the color to render, {@link config.TerminalColor}
   *
   * @return the formatted string with color
   */
  String print(String content, String color);
}
