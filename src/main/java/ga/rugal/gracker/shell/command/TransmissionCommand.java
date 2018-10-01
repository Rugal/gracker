package ga.rugal.gracker.shell.command;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import config.Constant;
import config.SystemDefaultProperty;

import ga.rugal.gracker.core.service.ConfigurationService;
import ga.rugal.gracker.core.service.HttpCredentialService;
import ga.rugal.gracker.core.service.ReferenceService;
import ga.rugal.gracker.util.LogUtil;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ThreeWayMerger;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.FileCopyUtils;

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
  private HttpCredentialService httpCredentialService;

  @Autowired
  private Git git;

  @Autowired
  private ReferenceService referenceService;

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
      LOG.debug("Fetch object from [{}] [{}]", fetch.getRemote(), fetch.getRefSpecs().get(0));
      result = this.doFetch(fetch);
    } catch (final TransportException e) {
      try {
        LOG.warn(Constant.SSL_ERROR);
        this.configurationService.setSslVerify(false);

        LOG.debug("Fetch object from [{}] [{}] by HTTP",
                  fetch.getRemote(),
                  fetch.getRefSpecs().get(0));
        result = this.doFetch(fetch);
      } catch (final GitAPIException ex) {
        LOG.error(Constant.UNABLE_REMOTE_HTTP, ex);
        return ex.getMessage();
      }
    } catch (final GitAPIException ex) {
      LOG.error(Constant.UNABLE_REMOTE, ex);
      return ex.getMessage();
    }
    LOG.trace("Fetch complete: [{}]", result.getMessages());
    return "Fetch complete";
  }

  @ShellMethod("Download and combine issue into current reference.")
  public int pull() {
    return 0;
  }

  /**
   * Push local changes to remote repository.
   *
   * @param remote remote repository name or URL
   * @param level  log level
   *
   * @return content to display
   *
   * @throws IOException unable to write to file system
   */
  @ShellMethod("Upload local issue to remote.")
  public String push(final @ShellOption(defaultValue = SystemDefaultProperty.DEFAULT_REMOTE,
                                        help = Constant.REMOTE_REPOSITORY) String remote,
                     final @ShellOption(defaultValue = Constant.ERROR,
                                        help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);

    final PushCommand pull = this.git.push()
      .setRemote(remote)
      .setRefSpecs(this.getRefSpec(remote));

    final String url = this.configurationService.getUrl(remote);
    if (null == url) {
      LOG.debug("Invalid URL for remote [{}]", remote);
      return String.format("Invalid URL for remote %s", remote);
    }
    if (url.startsWith("http")) {
      LOG.trace("Set credential since URL is HTTP");
      pull.setCredentialsProvider(this.httpCredentialService.getCredentialsProvider());
    }

    List<PushResult> result;
    try {
      LOG.debug("Push object to [{}] [{}]", pull.getRemote(), pull.getRefSpecs().get(0));
      result = Lists.newArrayList(pull.call());
    } catch (final TransportException e) {
      if (e.getMessage().endsWith("not authorized")) {
        return "Unauthorized access";
      }
      try {
        LOG.warn(Constant.SSL_ERROR);
        this.configurationService.setSslVerify(false);

        LOG.debug("Fetch object from [{}] [{}] by HTTP",
                  pull.getRemote(),
                  pull.getRefSpecs().get(0));
        result = Lists.newArrayList(pull.call());
      } catch (final GitAPIException ex) {
        LOG.error(Constant.UNABLE_REMOTE_HTTP, ex);
        return ex.getMessage();
      }
    } catch (final GitAPIException ex) {
      LOG.error(Constant.UNABLE_REMOTE, ex);
      return ex.getMessage();
    }

    LOG.trace("Push complete: [{}]", result.get(0).getMessages());
    return "Push complete";
  }

  /**
   * Rebase remote reference to local reference.
   *
   * @param remote name of remote repository
   * @param id     target issue id
   * @param level  log level
   *
   * @return content to display
   *
   * @throws IOException unable to access file system
   */
  @ShellMethod("Rebase to remote reference to local reference.")
  public String rebase(final @ShellOption(defaultValue = SystemDefaultProperty.DEFAULT_REMOTE,
                                          help = Constant.REMOTE_REPOSITORY) String remote,
                       final @ShellOption(defaultValue = Constant.NULL,
                                          help = Constant.ANY_FORMAT) String id,
                       final @ShellOption(defaultValue = Constant.ERROR,
                                          help = Constant.AVAILABLE_LEVEL) String level)
    throws IOException {

    LogUtil.setLogLevel(level);
    //get remote reference
    final Optional<Ref> remoteReference = this.referenceService.getDao().getRemote(remote, id);
    if (!remoteReference.isPresent()) {
      return String.format("Target issue id [%s] not exist in [%s], try fetch first", id, remote);
    }
    //check existence of local reference
    final Optional<Ref> localReference = this.referenceService.getDao().get(id);
    if (!localReference.isPresent()) {
      //just copy
      LOG.debug("Local reference not found for issue [{}]. Copy remote reference directly", id);
      FileCopyUtils.copy(this.referenceService.getRemoteReferenceFile(remote, id),
                         this.referenceService.getLocalReferenceFile(id));
      return String.format("Create issue [%s] from remote reference", id);
    }
    //find common parent commit
    final ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(this.git.getRepository());
    final boolean noConflict = merger.merge(localReference.get().getObjectId(),
                                            remoteReference.get().getObjectId());

    if (noConflict) {
      return String.format("Rebase complete, result tree id [%s]",
                           merger.getResultTreeId().getName());
    }

    return "Fail to rebase";
  }
}
