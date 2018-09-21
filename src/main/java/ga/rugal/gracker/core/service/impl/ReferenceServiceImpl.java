package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.Optional;

import config.Constant;
import config.SystemDefaultProperty;

import ga.rugal.gracker.core.dao.ReferenceDao;
import ga.rugal.gracker.core.service.ReferenceService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for reference service.
 *
 * @author Rugal Bernstein
 */
@Service
@Slf4j
public class ReferenceServiceImpl implements ReferenceService {

  @Autowired
  @Getter
  private ReferenceDao dao;

  /**
   * {@inheritDoc}
   */
  @Override
  public RefUpdate.Result create(final String name, final ObjectId commitId) throws IOException {
    return this.dao.create(String.format(SystemDefaultProperty.REFERENCE_TEMPLATE,
                                         Constant.REFERENCE,
                                         name),
                           commitId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ObjectId> getHead(final ObjectId id) throws IOException {
    LOG.debug("Get head commit object from reference [{}]", id.getName());
    final Optional<Ref> optional = this.dao.get(id.getName());
    return optional.isPresent()
           ? Optional.of(optional.get().getObjectId())
           : Optional.empty();
  }
}
