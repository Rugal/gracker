package ga.rugal.gracker.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import config.TestConstant;

import ga.rugal.IntegrationTestBase;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.service.TerminalService;

import com.google.common.collect.Lists;
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
public class ListTerminalServiceImplIntegrationTest extends IntegrationTestBase {

  @Autowired
  private Issue issue;

  @Autowired
  private Repository repository;

  @Resource(name = "list")
  private TerminalService list;

  private ObjectId id;

  private List<Issue> issues;

  @Before
  @SneakyThrows
  public void setUp() {
    this.id = this.repository.resolve(TestConstant.SAMPLE_ID);

    this.issue.getCommit().setId(this.id);

    this.issues = Lists.newArrayList(this.issue);
  }

  @SneakyThrows
  @Test
  public void print() {
    this.list.print(this.issues);
  }
}
