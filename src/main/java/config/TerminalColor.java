package config;

/**
 * List of color in terminal.
 *
 * @author Rugal Bernstein
 */
public interface TerminalColor {

  String RESET = "\u001B[0m";

  String BLACK_F = "\u001B[30m";

  String RED_F = "\u001B[31m";

  String GREEN_F = "\u001B[32m";

  String YELLOW_F = "\u001B[33m";

  String BLUE_F = "\u001B[34m";

  String PURPLE_F = "\u001B[35m";

  String CYAN_F = "\u001B[36m";

  String WHITE_F = "\u001B[37m";

  String BLACK_B = "\u001B[40m";

  String RED_B = "\u001B[41m";

  String GREEN_B = "\u001B[42m";

  String YELLOW_B = "\u001B[43m";

  String BLUE_B = "\u001B[44m";

  String PURPLE_B = "\u001B[45m";

  String CYAN_B = "\u001B[46m";

  String WHITE_B = "\u001B[47m";
}
