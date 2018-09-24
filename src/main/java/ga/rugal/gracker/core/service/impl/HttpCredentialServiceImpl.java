package ga.rugal.gracker.core.service.impl;

import java.io.Console;
import java.util.Objects;

import ga.rugal.gracker.core.service.HttpCredentialService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

/**
 * Implementation for HTTP credential service.
 *
 * @author Rugal Bernstein
 */
@Service
@Slf4j
public class HttpCredentialServiceImpl implements HttpCredentialService {

  @Setter
  private CredentialsProvider credentialsProvider;

  /**
   * {@inheritDoc}
   */
  @Override
  public CredentialsProvider getCredentialsProvider() {
    if (Objects.isNull(this.credentialsProvider)) {
      LOG.trace("Create and cache credential");
      final Console console = System.console();
      final String username = console.readLine("Username: ");
      final String password = new String(console.readPassword("Password: "));
      this.credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
    }
    return this.credentialsProvider;
  }
}
