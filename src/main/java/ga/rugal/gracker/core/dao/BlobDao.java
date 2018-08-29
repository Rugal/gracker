package ga.rugal.gracker.core.dao;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;

/**
 * Interface for Blob.
 *
 * @author Rugal Bernstein
 */
public interface BlobDao {

  /**
   * Create blob object.
   *
   * @param bytes input data
   *
   * @return object id
   *
   * @throws IOException whenever unable to write to file system
   */
  ObjectId create(final byte[] bytes) throws IOException;

  /**
   * Read blob.
   *
   * @param blobId object id
   *
   * @return object
   *
   * @throws IOException whenever unable to read from file system
   */
  ObjectLoader read(final ObjectId blobId) throws IOException;
}
