package ga.rugal.gracker.core.service;

import java.io.IOException;

import ga.rugal.gracker.core.dao.ReferenceDao;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;

/**
 * Interface for reference service.
 *
 * @author Rugal Bernstein
 */
public interface ReferenceService extends ServiceBase<ReferenceDao> {

  /**
   * Create reference with commit object.<BR>
   * This method will rename the actual name to be the format of {@code refs/issue/SHA1}
   *
   * @param name     name of the new reference, usually be the SHA1 of commit object id
   * @param commitId the commit object id
   *
   * @return the update result
   *
   * @throws IOException unable to write to file system
   */
  RefUpdate.Result create(String name, ObjectId commitId) throws IOException;
}
