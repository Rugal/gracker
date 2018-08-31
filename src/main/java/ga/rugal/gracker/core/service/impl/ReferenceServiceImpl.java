package ga.rugal.gracker.core.service.impl;

import ga.rugal.gracker.core.dao.ReferenceDao;
import ga.rugal.gracker.core.service.ReferenceService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for reference service.
 *
 * @author Rugal Bernstein
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

  @Autowired
  @Getter
  private ReferenceDao dao;
}
