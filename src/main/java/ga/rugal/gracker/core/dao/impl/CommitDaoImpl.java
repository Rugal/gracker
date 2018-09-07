package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.entity.Issue;

import lombok.Setter;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Object repository implementation.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
public class CommitDaoImpl implements CommitDao {

  @Autowired
  @Setter
  private Repository repository;

  @Autowired
  @Setter
  private PersonIdent personIdent;

  /**
   * Get person identity from issue user, otherwise, use default identity.
   *
   * @param user issue user
   *
   * @return actual identity
   */
  private PersonIdent getPersonIdent(final Issue.User user) {
    return (null == user)
           ? this.personIdent
           : new PersonIdent(user.getAuthor(), user.getEmail());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectId create(final Issue.Commit commit, final ObjectId treeId) throws IOException {
    final CommitBuilder commitBuilder = new CommitBuilder();
    commitBuilder.setTreeId(treeId);
    commitBuilder.setMessage(commit.getStatus().name());
    commitBuilder.setAuthor(this.getPersonIdent(commit.getAssigner()));
    commitBuilder.setCommitter(this.getPersonIdent(commit.getAssignee()));
    final ObjectInserter inserter = this.repository.newObjectInserter();
    final ObjectId commitId = inserter.insert(commitBuilder);
    inserter.flush();
    return commitId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RevCommit read(final ObjectId commit) throws IOException {
    final ObjectReader reader = this.repository.newObjectReader();
    final ObjectLoader loader = reader.open(commit);
    return RevCommit.parse(loader.getBytes());
  }
}
