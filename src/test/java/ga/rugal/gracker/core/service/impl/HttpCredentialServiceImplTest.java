package ga.rugal.gracker.core.service.impl;

import ga.rugal.UnitTestBase;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class HttpCredentialServiceImplTest extends UnitTestBase {

  @Autowired
  private HttpCredentialServiceImpl service;

  @Mock
  private CredentialsProvider credentialsProvider;

  @Before
  public void setUp() {
    this.service.setCredentialsProvider(this.credentialsProvider);
  }

  @Test
  public void getCredentialsProvider() {
    Assert.assertEquals(this.credentialsProvider, this.service.getCredentialsProvider());
  }
}
