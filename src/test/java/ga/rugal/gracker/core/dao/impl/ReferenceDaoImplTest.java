package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.UnitTestBase;
import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class ReferenceDaoImplTest extends UnitTestBase {

  private static final String NAME = "test";

  private final RefUpdate.Result result = RefUpdate.Result.FAST_FORWARD;

  @Autowired
  private Repository repository;

  @Autowired
  private ReferenceDaoImpl dao;

  @Mock
  private ObjectId id;

  @Mock
  private RefUpdate newUpdate;

  @Mock
  private RefDatabase refDatabase;

  @Before
  @SneakyThrows
  public void setUp() {
    this.dao.setRepository(this.repository);

    BDDMockito.given(this.repository.getRefDatabase()).willReturn(this.refDatabase);

    BDDMockito.given(this.refDatabase.newUpdate(BDDMockito.any(), BDDMockito.anyBoolean()))
      .willReturn(this.newUpdate);

    BDDMockito.given(this.newUpdate.update()).willReturn(this.result);
  }

  @SneakyThrows
  @Test
  public void create() {
    this.dao.create(NAME, this.id);

    BDDMockito.then(this.refDatabase).should(BDDMockito.times(1))
      .newUpdate(BDDMockito.any(), BDDMockito.anyBoolean());
    BDDMockito.then(this.newUpdate).should(BDDMockito.times(1)).update();
  }

  @SneakyThrows
  @Test(expected = IOException.class)
  public void create_IOException() {
    BDDMockito.given(this.refDatabase.newUpdate(BDDMockito.any(), BDDMockito.anyBoolean()))
      .willThrow(IOException.class);

    this.dao.create(NAME, this.id);

    BDDMockito.then(this.refDatabase).should(BDDMockito.times(1))
      .newUpdate(BDDMockito.any(), BDDMockito.anyBoolean());
    BDDMockito.then(this.newUpdate).should(BDDMockito.never()).update();
  }
}
