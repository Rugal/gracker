package ga.rugal.gracker.core.service;

import org.eclipse.jgit.transport.CredentialsProvider;

/**
 * Interface for HTTP credential service.
 *
 * @author Rugal Bernstein
 */
public interface HttpCredentialService {

  /**
   * Get credential for HTTP traffic.
   *
   * @return credential provider
   */
  CredentialsProvider getCredentialsProvider();
}
