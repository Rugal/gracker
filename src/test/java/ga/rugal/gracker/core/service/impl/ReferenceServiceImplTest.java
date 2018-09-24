package ga.rugal.gracker.core.service.impl;

import java.util.Optional;

import ga.rugal.UnitTestBase;
import ga.rugal.gracker.core.dao.ReferenceDao;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class ReferenceServiceImplTest extends UnitTestBase {

  private static final String CONTENT = "content";

  @Autowired
  private ReferenceServiceImpl service;

  @Mock
  private ReferenceDao dao;

  @Mock
  private Ref ref;

  @Mock
  private ObjectId id;

  @Before
  @SneakyThrows
  public void setUp() {
    this.service.setDao(this.dao);

    BDDMockito.given(this.dao.get(BDDMockito.any())).willReturn(Optional.of(this.ref));

    BDDMockito.given(this.ref.getObjectId()).willReturn(this.id);
  }

  @SneakyThrows
  @Test
  public void create() throws Exception {
    this.service.create(CONTENT, this.id);
  }

  @SneakyThrows
  @Test
  public void getHead() throws Exception {
    Assert.assertTrue(this.service.getHead(this.id).isPresent());
  }

  @SneakyThrows
  @Test
  public void getHead_not_found() throws Exception {
    BDDMockito.given(this.dao.get(BDDMockito.any())).willReturn(Optional.empty());

    Assert.assertFalse(this.service.getHead(this.id).isPresent());
  }
}
