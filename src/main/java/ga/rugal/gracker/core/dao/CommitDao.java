package ga.rugal.gracker.core.dao;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Interface for commit.
 *
 * @author Rugal Bernstein
 */
public interface CommitDao {

  ObjectId create(final String message, final ObjectId treeId) throws IOException;

  RevCommit read(final ObjectId commitId) throws IOException;
}
