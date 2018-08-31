package ga.rugal.gracker.core.service;

/**
 * Service base that returns the actual DAO object for each service.
 *
 * @param <T>
 *
 * @author Rugal Bernstein
 */
public interface ServiceBase<T> {

  /**
   * Get the Dao object of this service.
   *
   * @return
   */
  T getDao();
}
