package ga.rugal.gracker.core.service;

import java.io.IOException;
import java.util.List;

import ga.rugal.gracker.core.dao.BlobDao;

import org.eclipse.jgit.lib.ObjectId;

/**
 * Interface for commit service.
 *
 * @author Rugal Bernstein
 */
public interface BlobService extends ServiceBase<BlobDao> {

  /**
   * Create blob object for body.
   *
   * @param body input string
   *
   * @return blob
   *
   * @throws IOException unable to write to file system
   */
  ObjectId body(String body) throws IOException;

  /**
   * Create blob object for label.<BR>
   * label is stored in CSV format.
   *
   * @param label label array
   *
   * @return blob
   *
   * @throws IOException unable to write to file system
   */
  ObjectId label(List<String> label) throws IOException;

  /**
   * Create blob object for title.
   *
   * @param title input string
   *
   * @return blob
   *
   * @throws IOException unable to write to file system
   */
  ObjectId title(String title) throws IOException;

  /**
   * Read blob into a string, the target object must be blob.
   *
   * @param blobId blob object
   *
   * @return string typed data
   *
   * @throws IOException unable to read from file system
   */
  String read(ObjectId blobId) throws IOException;
}
