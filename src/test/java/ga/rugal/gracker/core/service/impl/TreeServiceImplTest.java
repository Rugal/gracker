package ga.rugal.gracker.core.service.impl;

import config.Constant;

import ga.rugal.UnitTestBase;
import ga.rugal.gracker.core.dao.TreeDao;
import ga.rugal.gracker.core.service.BlobService;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class TreeServiceImplTest extends UnitTestBase {

  @Autowired
  private TreeServiceImpl service;

  @Mock
  private TreeDao dao;

  @Mock
  private BlobService blobService;

  @Mock
  private TreeWalk treeWalk;

  @Mock
  private ObjectId treeId;

  @Before
  @SneakyThrows
  public void setUp() {
    this.service.setBlobService(this.blobService);
    this.service.setDao(this.dao);

    BDDMockito.given(this.dao.read(BDDMockito.any())).willReturn(this.treeWalk);

    BDDMockito.given(this.treeWalk.next()).willReturn(true, true, false);
    BDDMockito.given(this.treeWalk.getObjectId(0)).willReturn(this.treeId);
    BDDMockito.given(this.treeWalk.getPathString()).willReturn(Constant.TITLE, Constant.LABEL);

    BDDMockito.given(this.blobService.read(BDDMockito.any())).willReturn(Constant.BODY);
  }

  @SneakyThrows
  @Test
  public void read() {
    this.service.read(this.treeId);

    BDDMockito.then(this.blobService).should(BDDMockito.times(2)).read(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void read_false() {
    BDDMockito.given(this.treeWalk.next()).willReturn(false);

    this.service.read(this.treeId);

    BDDMockito.then(this.blobService).should(BDDMockito.never()).read(BDDMockito.any());
  }
}
