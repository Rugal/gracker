package ga.rugal.gracker.core.service;

import java.io.IOException;
import java.util.Optional;

import ga.rugal.gracker.core.dao.ReferenceDao;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

/**
 * Interface for reference service.
 *
 * @author Rugal Bernstein
 */
public interface ReferenceService extends ServiceBase<ReferenceDao> {

  /**
   * Create or update reference with commit object.<BR>
   * This method will rename the actual name to be the format of {@code refs/issue/SHA1}
   *
   * @param name     name of the new reference, usually be the SHA1 of commit object id
   * @param commitId the commit object
   *
   * @return the update result
   *
   * @throws IOException unable to write to file system
   */
  RefUpdate.Result create(String name, ObjectId commitId) throws IOException;

  /**
   * Get the head of this issue, which is the most recent commit object.
   *
   * @param id the issue/reference object
   *
   * @return optional reference object
   *
   * @throws IOException unable to read from file system
   */
  Optional<ObjectId> getHead(ObjectId id) throws IOException;

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
  Optional<Ref> getRemoteReference(String remote, String id) throws IOException;

  /**
   * Get local reference by its issue id.
   *
   * @param id issue id
   *
   * @return optional reference object
   *
   * @throws IOException unable to read from file system
   */
  Optional<Ref> getLocalReference(String id) throws IOException;
}
