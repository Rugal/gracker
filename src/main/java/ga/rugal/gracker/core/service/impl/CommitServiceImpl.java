package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.List;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.entity.Status;
import ga.rugal.gracker.core.service.BlobService;
import ga.rugal.gracker.core.service.CommitService;
import ga.rugal.gracker.core.service.TreeService;

import lombok.Getter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for commit service.
 *
 * @author Rugal Bernstein
 */
@Service
public class CommitServiceImpl implements CommitService {

  @Autowired
  @Getter
  private CommitDao dao;

  @Autowired
  private BlobService blobService;

  @Autowired
  private TreeService treeService;

  /**
   * {@inheritDoc}
   */
  @Override
  public RawIssue create(final Issue issue) throws IOException {
    final RawIssue rawIssue = new RawIssue();
    final RawIssue.Content content = new RawIssue.Content();
    content.setBody(this.blobService.body(issue.getContent().getBody()));
    content.setTitle(this.blobService.title(issue.getContent().getTitle()));
    final List<String> label = issue.getContent().getLabel();
    content.setLabel(label == null ? null : this.blobService.label(label));
    rawIssue.setContent(content);
    rawIssue.setTree(this.treeService.getDao().create(content));
    rawIssue.setCommit(this.dao.create(issue.getCommit(), rawIssue.getTree()));
    return rawIssue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Issue.Commit read(final ObjectId commitId) throws IOException {
    final RevCommit commit = this.dao.read(commitId);

    return Issue.builder()
      .assigner(new Issue.User(commit.getAuthorIdent().getName(),
                               commit.getAuthorIdent().getEmailAddress(),
                               commit.getAuthorIdent().getWhen().getTime() / 1000))
      .assignee(new Issue.User(commit.getCommitterIdent().getName(),
                               commit.getCommitterIdent().getEmailAddress(),
                               commit.getCommitTime()))
      .status(Status.valueOf(commit.getShortMessage()))
      //We use root commit id as issue id so we need to get it.
      .id(commit.getParentCount() == 0
          ? commitId
          : commit.getParent(commit.getParentCount() - 1).getId())
      .build()
      .getCommit();
  }
}
