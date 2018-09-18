package ga.rugal.gracker.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import javax.validation.constraints.NotNull;

import config.SystemDefaultProperty;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for string.
 *
 * @author Rugal Bernstein
 */
@Slf4j
public final class StringUtil {

  private StringUtil() {
  }

  /**
   * Get byte data of a string with UTF-8 encoding.
   *
   * @param content input string
   *
   * @return byte array
   */
  @NotNull
  public static byte[] getByte(final @NotNull String content) {
    byte[] data = null;
    try {
      data = content.getBytes(SystemDefaultProperty.ENCODE);
    } catch (final UnsupportedEncodingException ex) {
      //Not very likily to happen but we need to handle it
      LOG.error("Unable to encode with UTF-8", ex);
    }
    return data;
  }

  /**
   * String to upper case with locale handled.
   *
   * @param s input string
   *
   * @return upper cased string
   */
  public static String upperCase(final String s) {
    return s.toUpperCase(Locale.CANADA);
  }

  /**
   * String to lower case with locale handled.
   *
   * @param s input string
   *
   * @return upper cased string
   */
  public static String lowerCase(final String s) {
    return s.toLowerCase(Locale.CANADA);
  }

  /**
   * Center a string.
   *
   * @param s    target string
   * @param size width
   *
   * @return centered string
   */
  public static String center(final String s, final int size) {
    return center(s, size, ' ');
  }

  /**
   * Centering a string with padding.
   *
   * @param s    target string
   * @param size width
   * @param pad  character for padding
   *
   * @return centered string
   */
  public static String center(final String s, final int size, final char pad) {
    if (s == null || size <= s.length()) {
      return s;
    }

    final StringBuilder sb = new StringBuilder(size);
    for (int i = 0; i < (size - s.length()) / 2; i++) {
      sb.append(pad);
    }
    sb.append(s);
    while (sb.length() < size) {
      sb.append(pad);
    }
    return sb.toString();
  }

  /**
   * Get sub string.<BR>
   * If string is less than the target size just leave it as it is.
   *
   * @param s    target string
   * @param size maximum length
   *
   * @return sub string
   */
  public static String subString(final String s, final int size) {
    return s.length() > size
           ? s.substring(0, size)
           : s;
  }
}
