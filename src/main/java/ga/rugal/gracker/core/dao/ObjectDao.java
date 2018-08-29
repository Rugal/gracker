package ga.rugal.gracker.core.dao;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;

/**
 * Interface for Object.
 *
 * @author Rugal Bernstein
 */
public interface ObjectDao {

  /**
   * Create blob object.
   *
   * @param bytes input data
   *
   * @return
   *
   * @throws IOException whenever unable to write to file system
   */
  ObjectId create(final byte[] bytes) throws IOException;

  ObjectLoader read(final ObjectId blobId) throws IOException;
}
