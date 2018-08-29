package ga.rugal.gracker.core.dao;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Interface for Tree.
 *
 * @author Rugal Bernstein
 */
public interface TreeDao {

  ObjectId create(final String fileName, final ObjectId blobId) throws IOException;

  TreeWalk read(final ObjectId blobId) throws IOException;
}
