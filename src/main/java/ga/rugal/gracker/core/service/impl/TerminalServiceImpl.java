package ga.rugal.gracker.core.service.impl;

import config.TerminalColor;

import ga.rugal.gracker.core.service.TerminalService;

import org.springframework.stereotype.Service;

/**
 * Implementation for terminal service.<BR>
 * Responsible for printing colorful content in terminal.
 *
 * @author Rugal Bernstein
 */
@Service
public class TerminalServiceImpl implements TerminalService {

  /**
   * {@inheritDoc}
   */
  @Override
  public String print(final String content, final String color) {
    return String.format("%s%s%s", color, content, TerminalColor.RESET);
  }
}
