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
   * Create blob object from byte array.
   *
   * @param bytes input data
   *
   * @return object id
   *
   * @throws IOException whenever unable to write to file system
   */
  ObjectId create(byte[] bytes) throws IOException;

  /**
   * Read blob.
   *
   * @param blobId object id
   *
   * @return object
   *
   * @throws IOException whenever unable to read from file system
   */
  ObjectLoader read(ObjectId blobId) throws IOException;
}
