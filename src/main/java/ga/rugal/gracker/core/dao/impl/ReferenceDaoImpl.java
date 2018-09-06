package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.rugal.gracker.core.dao.ReferenceDao;

import lombok.Setter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Reference repository implementation.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
public class ReferenceDaoImpl implements ReferenceDao {

  @Autowired
  @Setter
  private RefDatabase refDatabase;

  @Override
  public RefUpdate.Result create(final String name, final String id) throws IOException {
    return this.create(name, ObjectId.fromString(id));
  }

  @Override
  public RefUpdate.Result create(final String name, final ObjectId objectId) throws IOException {
    final RefUpdate newUpdate = this.refDatabase.newUpdate(name, true);
    newUpdate.setNewObjectId(objectId);
    return newUpdate.update();
  }
}
