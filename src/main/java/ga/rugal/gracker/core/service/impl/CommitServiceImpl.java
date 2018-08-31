package ga.rugal.gracker.core.service.impl;

import ga.rugal.gracker.core.dao.CommitDao;
import ga.rugal.gracker.core.service.CommitService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for commit service.
 *
 * @author Rugal Bernstein
 */
@Service
public class CommitServiceImpl implements CommitService {

  @Autowired
  @Getter
  private CommitDao dao;
}
