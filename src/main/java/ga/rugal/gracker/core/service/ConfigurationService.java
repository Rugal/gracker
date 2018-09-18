package ga.rugal.gracker.core.service;

import java.io.IOException;

/**
 * Interface for configuration service.
 *
 * @author Rugal Bernstein
 */
public interface ConfigurationService {

  /**
   * Set HTTP SSL verification value for Git configuration.
   *
   * @param value to set SSL usage
   *
   * @throws IOException unable to write to file system
   */
  void setSslVerify(boolean value) throws IOException;

}
