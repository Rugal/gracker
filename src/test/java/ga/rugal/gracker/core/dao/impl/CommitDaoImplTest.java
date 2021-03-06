package ga.rugal.gracker.core.dao.impl;

import ga.rugal.UnitTestBase;
import ga.rugal.gracker.core.entity.Issue;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class CommitDaoImplTest extends UnitTestBase {

  @Autowired
  private Repository repository;

  @Autowired
  private Issue.Commit issueCommit;

  @Autowired
  private ObjectLoader loader;

  @Autowired
  private CommitDaoImpl dao;

  @Mock
  private ObjectInserter inserter;

  @Mock
  private ObjectReader reader;

  @Mock
  private ObjectId id;

  @Before
  @SneakyThrows
  public void setUp() {
    this.dao.setRepository(this.repository);
    BDDMockito.given(this.repository.newObjectInserter()).willReturn(this.inserter);
    BDDMockito.given(this.repository.newObjectReader()).willReturn(this.reader);

    BDDMockito.given(this.reader.open(BDDMockito.any())).willReturn(this.loader);
  }

  @SneakyThrows
  @Test
  public void create() {
    this.dao.create(this.issueCommit, this.id);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectInserter();
    BDDMockito.then(this.inserter).should(BDDMockito.times(1)).flush();
  }

  @SneakyThrows
  @Test
  public void update() {
    this.dao.update(this.issueCommit, this.id, this.id);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectInserter();
    BDDMockito.then(this.inserter).should(BDDMockito.times(1)).flush();
  }

  @SneakyThrows
  @Test
  public void update_null_personIdent() {
    this.issueCommit.setAssignee(null);

    this.dao.update(this.issueCommit, this.id, this.id);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectInserter();
    BDDMockito.then(this.inserter).should(BDDMockito.times(1)).flush();
  }

  @SneakyThrows
  @Test
  public void read() {
    this.dao.read(this.id);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectReader();
    BDDMockito.then(this.reader).should(BDDMockito.times(1)).open(BDDMockito.any());
  }
}
