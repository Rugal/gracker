package ga.rugal.gracker.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class for collection.
 *
 * @author Rugal Bernstein
 */
public final class CollectionUtil {

  private CollectionUtil() {
  }

  /**
   * Compare 2 collections.<BR>
   * This method will go through each collection by iterator and compare each element by its value
   * and order.
   *
   * @param a first
   * @param b second
   *
   * @return true if 1. both collections are null 2. both not null, element value and order are
   *         exactly same. Otherwise return false
   */
  public static boolean equals(final Collection a, final Collection b) {
    if (null == a && null == b) {
      return true;
    }
    if (null == a || null == b || a.size() != b.size()) {
      return false;
    }
    for (final Iterator iteratorA = a.iterator(), iteratorB = b.iterator();
         iteratorA.hasNext() && iteratorB.hasNext();) {
      final Object nextA = iteratorA.next();
      final Object nextB = iteratorB.next();
      if (!nextA.equals(nextB)) {
        return false;
      }
    }
    return true;
  }
}
