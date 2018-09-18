package ga.rugal.gracker.shell.command;

import java.io.IOException;

import config.Constant;
import config.SystemDefaultProperty;

import ga.rugal.gracker.core.service.ConfigurationService;
import ga.rugal.gracker.util.LogUtil;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Status and network related commands.
 *
 * @author Rugal Bernstein
 */
@ShellComponent
@Slf4j
public class TransmissionCommand {

  @Autowired
  private ConfigurationService configurationService;

  @Autowired
  private Git git;

  /**
   * Fulfill reference specification.
   *
   * @param remote current remote name
   *
   * @return formatted reference specification
   */
  private RefSpec getRefSpec(final String remote) {
    return new RefSpec()
      .setSourceDestination(String.format("refs/%s/*", Constant.REFERENCE),
                            String.format("refs/remotes/%s/%s/*", remote, Constant.REFERENCE));
  }

  /**
   * Actually fetch object from remote.
   *
   * @param fetch the fetch command object
   *
   * @return Result of fetch
   *
   * @throws GitAPIException Unable to call Git API
   */
  private FetchResult doFetch(final FetchCommand fetch) throws GitAPIException {
    LOG.trace("Listing branches before fetching:");
    this.git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call().stream()
      .forEach(r -> LOG.trace("Branch {}", r.getName()));

    final FetchResult result = fetch.call();

    LOG.trace("Listing branches after fetching:");
    this.git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call().stream()
      .forEach(r -> LOG.trace("Branch {}", r.getName()));

    return result;
  }

  /**
   * Download git object from remote repository and save it under local remote reference.
   *
   * @param remote remote repository name or URL
   * @param force  enable force update
   * @param prune  prune deleted reference
   * @param level  log level
   *
   * @return content to display
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("Download issue information.")
  public String fetch(final @ShellOption(defaultValue = SystemDefaultProperty.DEFAULT_REMOTE,
                                         help = Constant.REMOTE_REPOSITORY) String remote,
                      final @ShellOption(defaultValue = "false") boolean force,
                      final @ShellOption(defaultValue = "false") boolean prune,
                      final @ShellOption(defaultValue = Constant.ERROR,
                                         help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);

    final FetchCommand fetch = this.git.fetch()
      .setRemote(remote)
      .setRefSpecs(this.getRefSpec(remote))
      .setCheckFetchedObjects(true)
      .setForceUpdate(force)
      .setRemoveDeletedRefs(prune);

    FetchResult result;
    try {
      LOG.debug("Fetch object from {} {}", fetch.getRemote(), fetch.getRefSpecs().get(0));
      result = this.doFetch(fetch);
    } catch (final TransportException e) {
      try {
        LOG.warn("Ssl certification error, try again with HTTP instead");
        this.configurationService.setSslVerify(true);

        LOG.debug("Fetch object from {} {} by HTTP", fetch.getRemote(), fetch.getRefSpecs().get(0));
        result = this.doFetch(fetch);
      } catch (final GitAPIException ex) {
        LOG.error("Unable to download object from remote even using HTTP", ex);
        return ex.getMessage();
      }
    } catch (final GitAPIException ex) {
      LOG.error("Unable to download object from remote", ex);
      return ex.getMessage();
    }
    LOG.trace("Fetch complete");
    return result.getMessages();
  }

  @ShellMethod("Download and combine issue into current reference.")
  public int pull() {
    return 0;
  }

  @ShellMethod("Upload local issue to remote.")
  public int push(final String id) {
    return 0;
  }
}
