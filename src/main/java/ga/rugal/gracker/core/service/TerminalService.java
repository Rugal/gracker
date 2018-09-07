package ga.rugal.gracker.core.service;

import java.util.List;

import ga.rugal.gracker.core.entity.Issue;

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

  /**
   * Print issue in list.
   *
   * @param issues list of issue
   *
   * @return content to be printed
   */
  String print(List<Issue> issues);
}
