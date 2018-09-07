package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.service.CommitService;
import ga.rugal.gracker.core.service.IssueService;
import ga.rugal.gracker.core.service.ReferenceService;
import ga.rugal.gracker.core.service.TreeService;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for issue service.
 *
 * @author Rugal Bernstein
 */
@Service
@Slf4j
public class IssueServiceImpl implements IssueService {

  @Autowired
  private CommitService commitService;

  @Autowired
  private TreeService treeService;

  @Autowired
  private ReferenceService referenceService;

  /**
   * {@inheritDoc}
   */
  @Override
  public RawIssue create(final Issue issue) throws IOException {
    final RawIssue rawIssue = this.commitService.create(issue);
    this.referenceService.create(rawIssue.getCommit().getName(), rawIssue.getCommit());
    return rawIssue;
  }

  /**
   * Get one issue by object id.
   *
   * @param commitObject commit object
   *
   * @return assembled issue object
   *
   * @throws IOException unable to read from file system
   */
  public Issue get(final RevCommit commitObject) throws IOException {
    return Issue.builder()
      .commit(this.commitService.read(commitObject))
      .content(this.treeService.read(commitObject.getTree()))
      .build();
  }

  private Issue getWithoutException(final ObjectId id) {
    try {
      return this.get(this.commitService.getDao().read(id));
    } catch (final IOException ex) {
      LOG.error("Unable to read from file system", ex);
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Issue> getAllIssue() throws IOException {
    final List<Ref> all = this.referenceService.getDao().getAll();
    LOG.debug("Find {} issue(s)", all.size());
    return all.stream()
      .map(Ref::getObjectId)
      .filter(Objects::nonNull)
      .map(id -> this.getWithoutException(id))
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
  }
}
