package ga.rugal.gracker.core.dao;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;

/**
 * Interface for reference.
 *
 * @author Rugal Bernstein
 */
public interface ReferenceDao {

  RefUpdate.Result create(String name, String id) throws IOException;

  RefUpdate.Result create(String name, ObjectId commitId) throws IOException;
}
