package ga.rugal.gracker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for date.
 *
 * @author Rugal Bernstein
 */
public class DateUtil {

  private DateUtil() {
  }

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  private static final String TIME_FORMAT = "HH:mm:ss";

  private static final String FULL_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

  /**
   * Convert date to string in date only format.
   *
   * @param date Date to covert
   *
   * @return String representation
   */
  public static String getDateString(final Date date) {
    return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  /**
   * Convert date to string in date only format.
   *
   * @param unixTime unix time
   *
   * @return String representation
   */
  public static String getDateString(final long unixTime) {
    return getDateString(new Date(unixTime * 1000));
  }

  /**
   * Convert times to string in time only format.
   *
   * @param date Date to covert
   *
   * @return String representation
   */
  public static String getTimeString(final Date date) {
    return new SimpleDateFormat(TIME_FORMAT).format(date);
  }

  /**
   * Convert date to string in time only format.
   *
   * @param unixTime unix time
   *
   * @return String representation
   */
  public static String getTimeString(final long unixTime) {
    return getTimeString(new Date(unixTime * 1000));
  }

  /**
   * Convert times to string in full format.
   *
   * @param date Date to covert
   *
   * @return String representation
   */
  public static String getFullString(final Date date) {
    return new SimpleDateFormat(FULL_FORMAT).format(date);
  }

  /**
   * Convert date to string in full format.
   *
   * @param unixTime unix time
   *
   * @return String representation
   */
  public static String getFullString(final long unixTime) {
    return getFullString(new Date(unixTime * 1000));
  }
}
