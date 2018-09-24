package ga.rugal.gracker.core.service.impl;

import config.SystemDefaultProperty;

import ga.rugal.UnitTestBase;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class ConfigurationServiceImplTest extends UnitTestBase {

  @Autowired
  private ConfigurationServiceImpl service;

  @Mock
  private Repository repository;

  @Mock
  private StoredConfig config;

  @Before
  public void setUp() {
    this.service.setRepository(this.repository);

    BDDMockito.given(this.repository.getConfig()).willReturn(this.config);
  }

  @SneakyThrows
  @Test
  public void setSslVerify() {
    this.service.setSslVerify(true);
  }

  @Test
  public void getUrl() {
    this.service.getUrl(SystemDefaultProperty.DEFAULT_REMOTE);
  }
}
