package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
   * Parse and get commit id into {@code Issue} format.
   *
   * @param commitId commit object id
   *
   * @return assembled issue object
   *
   * @throws IOException unable to read from file system
   */
  private Issue get(final ObjectId commitId) throws IOException {
    return this.get(this.commitService.getDao().read(commitId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Issue get(final RevCommit revCommit) throws IOException {
    return Issue.builder()
      .commit(this.commitService.read(revCommit))
      .content(this.treeService.read(revCommit.getTree()))
      .build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Issue> get(final String id) throws IOException {
    final Optional<Ref> optional = this.referenceService.getDao().get(id);

    return optional.isPresent()
           ? Optional.of(this.get(optional.get().getObjectId()))
           : Optional.empty();
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
