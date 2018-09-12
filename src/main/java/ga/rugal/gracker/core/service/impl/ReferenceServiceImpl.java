package ga.rugal.gracker.core.service.impl;

import java.io.IOException;

import config.Constant;

import ga.rugal.gracker.core.dao.ReferenceDao;
import ga.rugal.gracker.core.service.ReferenceService;

import lombok.Getter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for reference service.
 *
 * @author Rugal Bernstein
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

  private static final String TEMPLATE = "refs/%s/%s";

  @Autowired
  @Getter
  private ReferenceDao dao;

  @Override
  public RefUpdate.Result create(final String name, final ObjectId commitId) throws IOException {
    return this.dao.create(String.format(TEMPLATE, Constant.REFERENCE, name), commitId);
  }
}
