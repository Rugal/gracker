package ga.rugal.gracker.core.service;

import config.TerminalColor;

/**
 * Interface for terminal service.
 *
 * @author Rugal Bernstein
 * @param <T> The content type to be printed
 */
public interface TerminalService<T> {

  /**
   * Print content to terminal with color.
   *
   * @param content the content to be printed
   * @param color   the color to render, {@link config.TerminalColor}
   *
   * @return the formatted string with color
   */
  default String print(final String content, final String color) {
    return String.format("%s%s%s", color, content, TerminalColor.RESET);
  }

  /**
   * Print any content in console.
   *
   * @param content
   *
   * @return
   */
  String print(T content);
}
