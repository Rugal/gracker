package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.UnitTestBase;
import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class BlobDaoImplTest extends UnitTestBase {

  private Repository repository;

  @Autowired
  private BlobDaoImpl dao;

  @Mock
  private ObjectInserter inserter;

  @Mock
  private ObjectReader reader;

  @Mock
  private ObjectId id;

  @Before
  @SneakyThrows
  public void setUp() {
    this.repository = this.dao.getRepository();
    BDDMockito.given(repository.newObjectInserter()).willReturn(this.inserter);
    BDDMockito.given(repository.newObjectReader()).willReturn(this.reader);
    BDDMockito.given(this.inserter.insert(BDDMockito.anyInt(), BDDMockito.any()))
      .willReturn(this.id);
  }

  @SneakyThrows
  @Test
  public void create() {
    this.dao.create(null);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectInserter();
    BDDMockito.then(this.inserter).should(BDDMockito.times(1))
      .insert(BDDMockito.anyInt(), BDDMockito.any());
    BDDMockito.then(this.inserter).should(BDDMockito.times(1)).flush();
  }

  @SneakyThrows
  @Test(expected = IOException.class)
  public void create_IOException() {
    BDDMockito.given(this.inserter.insert(BDDMockito.anyInt(), BDDMockito.any()))
      .willThrow(IOException.class);

    this.dao.create(null);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectInserter();
    BDDMockito.then(this.inserter).should(BDDMockito.times(1))
      .insert(BDDMockito.anyInt(), BDDMockito.any());
    BDDMockito.then(this.inserter).should(BDDMockito.never()).flush();
  }

  @SneakyThrows
  @Test
  public void read() {
    dao.read(null);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectReader();
    BDDMockito.then(this.reader).should(BDDMockito.times(1)).open(BDDMockito.any());
  }

  @SneakyThrows
  @Test(expected = IOException.class)
  public void read_IOException() {
    BDDMockito.given(this.reader.open(BDDMockito.any())).willThrow(IOException.class);

    dao.read(null);

    BDDMockito.then(this.repository).should(BDDMockito.times(1)).newObjectReader();
    BDDMockito.then(this.reader).should(BDDMockito.times(1)).open(BDDMockito.any());
  }
}
