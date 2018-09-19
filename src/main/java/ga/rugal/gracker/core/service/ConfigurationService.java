package ga.rugal.gracker.core.service;

import java.io.IOException;
import javax.annotation.Nullable;

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

  /**
   * get URL of remote.
   *
   * @param remote name of remote
   *
   * @return URL
   */
  @Nullable
  String getUrl(String remote);
}
