package ga.rugal.gracker.core.service.impl;

import ga.rugal.gracker.core.dao.TreeDao;
import ga.rugal.gracker.core.service.TreeService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for tree service.
 *
 * @author Rugal Bernstein
 */
@Service
public class TreeServiceImpl implements TreeService {

  @Autowired
  @Getter
  private TreeDao dao;
}
