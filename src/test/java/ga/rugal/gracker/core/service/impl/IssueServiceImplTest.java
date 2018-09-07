package ga.rugal.gracker.core.service.impl;

import ga.IntegrationTestBase;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class IssueServiceImplTest extends IntegrationTestBase {

  @Autowired
  private Repository repository;

  @Autowired
  private IssueServiceImpl issueService;

  @Before
  public void setUp() {
  }

  @Test
  public void testGetIssue() throws Exception {
  }
}
