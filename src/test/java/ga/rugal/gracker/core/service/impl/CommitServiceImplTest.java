package ga.rugal.gracker.core.service.impl;

import java.util.Optional;

import ga.rugal.UnitTestBase;
import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.dao.TreeDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.exception.IssueNotFoundException;
import ga.rugal.gracker.core.service.BlobService;
import ga.rugal.gracker.core.service.ReferenceService;
import ga.rugal.gracker.core.service.TreeService;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class CommitServiceImplTest extends UnitTestBase {

  @Autowired
  private CommitServiceImpl service;

  @Autowired
  private ObjectLoader objectLoader;

  @Autowired
  private Issue issue;

  private RevCommit commit;

  @Mock
  private CommitDao dao;

  @Mock
  private ObjectId commitId;

  @Mock
  private TreeDao treeDao;

  @Mock
  private BlobService blobService;

  @Mock
  private TreeService treeService;

  @Mock
  private ReferenceService referenceService;

  @Before
  @SneakyThrows
  public void setUp() {
    this.service.setDao(this.dao);
    this.service.setBlobService(this.blobService);
    this.service.setReferenceService(this.referenceService);
    this.service.setTreeService(this.treeService);

    this.commit = RevCommit.parse(this.objectLoader.getBytes());

    BDDMockito.given(this.dao.read(BDDMockito.any())).willReturn(this.commit);

    BDDMockito.given(this.blobService.body(BDDMockito.any())).willReturn(this.commitId);
    BDDMockito.given(this.blobService.title(BDDMockito.any())).willReturn(this.commitId);
    BDDMockito.given(this.blobService.label(BDDMockito.any())).willReturn(this.commitId);

    BDDMockito.given(this.treeService.getDao()).willReturn(this.treeDao);

    BDDMockito.given(this.treeDao.create(BDDMockito.any())).willReturn(this.commitId);

    BDDMockito.given(this.referenceService.getHead(BDDMockito.any()))
      .willReturn(Optional.of(this.commitId));
  }

  @SneakyThrows
  @Test
  public void read() {
    this.service.read(this.commitId);

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).read(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void create() {
    this.service.create(this.issue);

    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).title(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).body(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).label(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void create_no_label() {
    this.issue.getContent().setLabel(null);

    this.service.create(this.issue);

    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).title(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).body(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.never()).label(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void update() {
    this.service.update(this.issue);

    BDDMockito.then(this.referenceService).should(BDDMockito.times(1)).getHead(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).title(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).body(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.times(1)).label(BDDMockito.any());
  }

  @SneakyThrows
  @Test(expected = IssueNotFoundException.class)
  public void update_IssueNotFoundException() {
    BDDMockito.given(this.referenceService.getHead(BDDMockito.any()))
      .willReturn(Optional.empty());

    this.service.update(this.issue);

    BDDMockito.then(this.referenceService).should(BDDMockito.times(1)).getHead(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.never()).title(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.never()).body(BDDMockito.any());
    BDDMockito.then(this.blobService).should(BDDMockito.never()).label(BDDMockito.any());
  }
}
