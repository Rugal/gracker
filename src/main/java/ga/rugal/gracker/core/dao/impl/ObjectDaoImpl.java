package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.rugal.gracker.core.dao.ObjectDao;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Object repository implementation.
 *
 * @author Rugal Bernstein
 */
@org.springframework.stereotype.Repository
public class ObjectDaoImpl implements ObjectDao {

  @Autowired
  private Repository repository;

  @Override
  public ObjectId create(final byte[] bytes) throws IOException {
    final ObjectInserter inserter = this.repository.newObjectInserter();
    final ObjectId blobId = inserter.insert(Constants.OBJ_BLOB, bytes);
    inserter.flush();
    return blobId;
  }

  @Override
  public ObjectLoader read(final ObjectId blobId) throws IOException {
    final ObjectReader objectReader = this.repository.newObjectReader();
    return objectReader.open(blobId);
  }
}
