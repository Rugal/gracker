package ga.rugal.gracker.util;

import java.io.UnsupportedEncodingException;
import javax.validation.constraints.NotNull;

import config.SystemDefaultProperty;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for string.
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class StringUtil {

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
}
