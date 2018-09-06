package ga.rugal.gracker.core.dao;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

/**
 * Interface for reference.
 *
 * @author Rugal Bernstein
 */
public interface ReferenceDao {

  /**
   * Create new reference with commit object id.
   *
   * @param name reference name
   * @param id   commit object id
   *
   * @return creation status
   *
   * @throws IOException unable to write to file system
   */
  RefUpdate.Result create(String name, String id) throws IOException;

  /**
   * Create new reference with commit object.
   *
   * @param name     reference name
   * @param commitId commit object
   *
   * @return creation status
   *
   * @throws IOException unable to write to file system
   */
  RefUpdate.Result create(String name, ObjectId commitId) throws IOException;

  /**
   * Get all existing references relevant to issue.
   *
   * @return reference list
   *
   * @throws IOException unable to read from file system
   */
  List<Ref> getAll() throws IOException;
}
