package ga.rugal.gracker.core.service.impl;

import javax.annotation.Resource;

import config.TestConstant;

import ga.rugal.IntegrationTestBase;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.service.TerminalService;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class DetailTerminalServiceImplIntegrationTest extends IntegrationTestBase {

  @Autowired
  private Issue issue;

  @Autowired
  private Repository repository;

  @Resource(name = "detail")
  private TerminalService detail;

  private ObjectId id;

  @Before
  @SneakyThrows
  public void setUp() {
    this.id = this.repository.resolve(TestConstant.SAMPLE_ID);

    this.issue.getCommit().setId(this.id);
  }

  @SneakyThrows
  @Test
  public void print() {
    this.detail.print(this.issue);
  }

  @SneakyThrows
  @Test
  public void print_no_label() {
    this.issue.getContent().setLabel(null);

    this.detail.print(this.issue);
  }
}
