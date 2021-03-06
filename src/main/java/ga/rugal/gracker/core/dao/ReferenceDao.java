package ga.rugal.gracker.core.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

  /**
   * Get local reference by any format of id.
   *
   * @param id in any format
   *
   * @return optional reference
   *
   * @throws IOException unable to read from file system
   */
  Optional<Ref> get(String id) throws IOException;

  /**
   * Get remote reference by its issue id.
   *
   * @param remote remote name
   * @param id     issue id
   *
   * @return optional reference object
   *
   * @throws IOException unable to read from file system
   */
  Optional<Ref> getRemote(String remote, String id) throws IOException;
}
