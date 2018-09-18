package ga.rugal.gracker.core.service.impl;

import java.io.IOException;

import ga.rugal.gracker.core.service.ConfigurationService;

import org.eclipse.jgit.lib.StoredConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for configuration service.
 *
 * @author Rugal Bernstein
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

  @Autowired
  private StoredConfig storeConfig;

  /**
   * Set HTTP SSL verification value for Git configuration.
   *
   * @param value to set SSL usage
   *
   * @throws IOException unable to write to file system
   */
  @Override
  public void setSslVerify(final boolean value) throws IOException {
    this.storeConfig.setBoolean("http", null, "sslVerify", value);
    this.storeConfig.save();
  }
}
