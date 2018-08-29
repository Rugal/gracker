package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.dao.ReferenceDao;
import ga.rugal.gracker.core.dao.TreeDao;

import ga.IntegrationTestBase;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ga.rugal.gracker.core.dao.BlobDao;

/**
 *
 * @author Rugal Bernstein
 */
@Ignore
public class IntegrationImplIntegrationTest extends IntegrationTestBase {

  @Autowired
  private BlobDao objectDao;

  @Autowired
  private TreeDao treeDao;

  @Autowired
  private CommitDao commitDao;

  @Autowired
  private ReferenceDao referenceDao;

  @Before
  public void setUp() {
  }

  @Test
  public void create() throws IOException {
    final byte[] bytes = "Rugal Bernstein".getBytes(SystemDefaultProperty.ENCODE);
    final ObjectId objectId = this.objectDao.create(bytes);
    System.out.println(objectId.getName());
    final ObjectId treeId = this.treeDao.create("data.txt", objectId);
    System.out.println(treeId.getName());
    final ObjectId commitId = this.commitDao.create("", treeId);
    System.out.println(commitId.getName());
    this.referenceDao.create("refs/rugal/master", commitId);
  }
}
