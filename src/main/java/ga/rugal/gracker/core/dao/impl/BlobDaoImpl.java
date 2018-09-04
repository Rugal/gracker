package ga.rugal.gracker.core.dao.impl;

import java.io.IOException;

import ga.rugal.gracker.core.dao.BlobDao;

import lombok.Getter;
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
public class BlobDaoImpl implements BlobDao {

  @Autowired
  @Getter
  private Repository repository;

  /**
   * {@inheritDoc }
   */
  @Override
  public ObjectId create(final byte[] bytes) throws IOException {
    final ObjectInserter inserter = this.repository.newObjectInserter();
    final ObjectId blobId = inserter.insert(Constants.OBJ_BLOB, bytes);
    inserter.flush();
    return blobId;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public ObjectLoader read(final ObjectId blobId) throws IOException {
    final ObjectReader reader = this.repository.newObjectReader();
    return reader.open(blobId);
  }
}
