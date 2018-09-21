package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.entity.Status;
import ga.rugal.gracker.core.exception.IssueNotFoundException;
import ga.rugal.gracker.core.service.BlobService;
import ga.rugal.gracker.core.service.CommitService;
import ga.rugal.gracker.core.service.ReferenceService;
import ga.rugal.gracker.core.service.TreeService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CommitServiceImpl implements CommitService {

  @Autowired
  @Getter
  private CommitDao dao;

  @Autowired
  private BlobService blobService;

  @Autowired
  private TreeService treeService;

  @Autowired
  private ReferenceService referenceService;

  /**
   * {@inheritDoc}
   */
  @Override
  public RawIssue create(final Issue issue) throws IOException {
    LOG.trace("Create commit");
    final RawIssue rawIssue = this.doCreate(issue);
    rawIssue.setCommit(this.dao.create(issue.getCommit(), rawIssue.getTree()));
    return rawIssue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Issue.Commit read(final ObjectId commitId) throws IOException {
    LOG.trace("Read commit");
    final RevCommit commit = this.dao.read(commitId);
    final RevCommit root = this.getRoot(commit);
    if (Objects.nonNull(root)) {
      LOG.debug("Commit [{}] is under issue [{}]", commitId.getName(), root.getName());
    }

    LOG.trace("Build commit");
    return Issue.builder()
      .assigner(new Issue.User(commit.getAuthorIdent().getName(),
                               commit.getAuthorIdent().getEmailAddress(),
                               commit.getAuthorIdent().getWhen().getTime() / 1000))
      .assignee(new Issue.User(commit.getCommitterIdent().getName(),
                               commit.getCommitterIdent().getEmailAddress(),
                               commit.getCommitTime()))
      .status(Status.valueOf(commit.getShortMessage()))
      //We use root commit id as issue id so we need to get it.
      .id(root)
      .build()
      .getCommit();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RawIssue update(final Issue issue) throws IOException, IssueNotFoundException {
    LOG.trace("Update commit");
    final Optional<ObjectId> optional = this.referenceService.getHead(issue.getCommit().getId());
    if (!optional.isPresent()) {
      LOG.error("Issue [{}] not found", issue.getCommit().getId());
      throw new IssueNotFoundException();
    }
    //similar to creation
    final RawIssue rawIssue = this.doCreate(issue); //the current head
    rawIssue.setCommit(this.dao.update(issue.getCommit(), rawIssue.getTree(), optional.get()));
    return rawIssue;
  }

  /**
   * Follow the first parent to traverse back to the very first commit, so as to find the reference
   * id.
   *
   * @param head current object id
   *
   * @return it is a revision commit object, also, its SHA-1 code is the reference name
   *
   * @throws IOException unable to read from file system
   */
  private RevCommit getRoot(final RevCommit head) throws IOException {
    LOG.trace("Get root commit to find issue id");
    RevCommit commit;
    for (commit = head; commit.getParents() != null && commit.getParentCount() > 0;) {
      LOG.debug("Traverse at {}", commit.getName());
      commit = this.dao.read(commit.getParent(0));
    }
    LOG.debug("Traverse {} back to the very first commit {}", head.getName(), commit.getName());
    return commit;
  }

  private RawIssue doCreate(final Issue issue) throws IOException {
    LOG.trace("Actually create issue");
    final RawIssue rawIssue = new RawIssue();
    final RawIssue.Content content = new RawIssue.Content();
    content.setBody(this.blobService.body(issue.getContent().getBody()));
    content.setTitle(this.blobService.title(issue.getContent().getTitle()));
    final List<String> label = issue.getContent().getLabel();
    if (null != label) {
      LOG.trace("Sort and add label to issue");
      Collections.sort(label);
      content.setLabel(this.blobService.label(label));
    }
    rawIssue.setContent(content);
    rawIssue.setTree(this.treeService.getDao().create(content));
    return rawIssue;
  }
}
