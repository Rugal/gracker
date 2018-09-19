package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import javax.annotation.Nullable;

import ga.rugal.gracker.core.service.ConfigurationService;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for configuration service.
 *
 * @author Rugal Bernstein
 */
@Service
@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {

  @Autowired
  private Repository repository;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSslVerify(final boolean value) throws IOException {
    final StoredConfig config = this.repository.getConfig();
    config.setBoolean("http", null, "sslVerify", value);
    config.save();
    LOG.info("Set http.sslVerify to {}", value);
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  @Override
  public String getUrl(final String remote) {
    final StoredConfig config = this.repository.getConfig();
    return config.getString("remote", remote, "url");
  }
}
