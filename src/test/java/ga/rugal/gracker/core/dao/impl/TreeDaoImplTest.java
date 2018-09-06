package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.rugal.gracker.core.entity.RawIssue;

import ga.UnitTestBase;
import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class TreeDaoImplTest extends UnitTestBase {

  @Autowired
  private Repository repository;

  @Autowired
  private TreeDaoImpl dao;

  @Autowired
  private RawIssue.Content rawContent;

  @Mock
  private ObjectInserter inserter;

  @Mock
  private ObjectReader reader;

  @Before
  @SneakyThrows
  public void setUp() {
    this.dao.setRepository(this.repository);
    BDDMockito.given(this.repository.newObjectInserter()).willReturn(this.inserter);
    BDDMockito.given(this.repository.newObjectReader()).willReturn(this.reader);
    Assert.assertNotNull(this.inserter);
  }

  @SneakyThrows
  @Test
  public void create() {
    this.dao.create(this.rawContent);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectInserter();
  }

  /**
   * Ignore it as it contains instantiation.
   */
  @Ignore
  @SneakyThrows
  @Test
  public void read() {
    this.dao.read(null);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectReader();
    BDDMockito.then(this.reader).should(BDDMockito.times(1)).open(BDDMockito.any());
  }

  /**
   * Ignore it as it contains instantiation.
   */
  @Ignore
  @SneakyThrows
  @Test(expected = IOException.class)
  public void read_IOException() {
    BDDMockito.given(this.reader.open(BDDMockito.any())).willThrow(IOException.class);

    this.dao.read(null);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectReader();
    BDDMockito.then(this.reader).should(BDDMockito.times(1)).open(BDDMockito.any());
  }
}
