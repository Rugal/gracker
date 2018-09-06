package ga.rugal.gracker.core.dao.impl;

import ga.rugal.gracker.core.dao.BlobDao;

import ga.IntegrationTestBase;
import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class BlobDaoImplIntegrationTest extends IntegrationTestBase {

  private ObjectId id;

  @Autowired
  private byte[] testData;

  @Autowired
  private BlobDao dao;

  @Before
  @SneakyThrows
  public void setUp() {
    this.id = this.dao.create(this.testData);
  }

  @Test
  public void create() {
    Assert.assertNotNull(this.id);
  }

  @SneakyThrows
  @Test
  public void reads() {
    Assert.assertNotNull(this.dao.read(this.id));
  }
}
