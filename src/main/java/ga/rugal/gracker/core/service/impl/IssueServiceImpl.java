package ga.rugal.gracker.core.service.impl;

import java.io.IOException;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.core.service.CommitService;
import ga.rugal.gracker.core.service.IssueService;
import ga.rugal.gracker.core.service.ReferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for issue service.
 *
 * @author Rugal Bernstein
 */
@Service
public class IssueServiceImpl implements IssueService {

  @Autowired
  private CommitService commitService;

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
}
