package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import config.Constant;
import config.SystemDefaultProperty;

import ga.rugal.gracker.core.dao.ReferenceDao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of Reference repository.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
@Slf4j
public class ReferenceDaoImpl implements ReferenceDao {

  @Autowired
  @Setter
  private Repository repository;

  private Optional<Ref> doGetReference(final String prefix) throws IOException {
    final List<Ref> refs = this.repository.getRefDatabase().getRefsByPrefix(prefix);
    return refs.isEmpty()
           ? Optional.empty()
           : Optional.of(refs.get(0));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RefUpdate.Result create(final String name, final String id) throws IOException {
    return this.create(name, ObjectId.fromString(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RefUpdate.Result create(final String name, final ObjectId commit) throws IOException {
    LOG.debug("Create/Update reference [{}] for commit [{}]", name, commit.getName());
    final RefUpdate newUpdate = this.repository.getRefDatabase().newUpdate(name, true);
    newUpdate.setNewObjectId(commit);
    return newUpdate.update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Ref> getAll() throws IOException {
    LOG.trace("Get all references");
    final String prefix = String.format("refs/%s", Constant.REFERENCE);
    return this.repository.getRefDatabase().getRefsByPrefix(prefix);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Ref> get(final String id) throws IOException {
    final String prefix = String.format(SystemDefaultProperty.REFERENCE_TEMPLATE,
                                        Constant.REFERENCE,
                                        id);
    LOG.debug("Get local reference [{}]", prefix);
    return this.doGetReference(prefix);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Ref> getRemote(final String remote, final String id) throws IOException {
    final String prefix = String.format(SystemDefaultProperty.REMOTE_REFERENCE_TEMPLATE,
                                        remote,
                                        Constant.REFERENCE,
                                        id);
    LOG.debug("Get remote reference [{}]", prefix);
    return this.doGetReference(prefix);
  }
}
