package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import config.Constant;

import ga.rugal.gracker.core.dao.ReferenceDao;

import lombok.Setter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of Reference repository.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
public class ReferenceDaoImpl implements ReferenceDao {

  @Autowired
  @Setter
  private RefDatabase refDatabase;

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
  public RefUpdate.Result create(final String name, final ObjectId objectId) throws IOException {
    final RefUpdate newUpdate = this.refDatabase.newUpdate(name, true);
    newUpdate.setNewObjectId(objectId);
    return newUpdate.update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Ref> getAll() throws IOException {
    final String prefix = String.format("refs/%s", Constant.REFERENCE);
    return this.refDatabase.getRefsByPrefix(prefix);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Ref> get(final String id) throws IOException {
    final String prefix = String.format("refs/%s/%s", Constant.REFERENCE, id);
    final List<Ref> refs = this.refDatabase.getRefsByPrefix(prefix);
    return refs.isEmpty()
           ? Optional.empty()
           : Optional.of(refs.get(0));
  }

}
