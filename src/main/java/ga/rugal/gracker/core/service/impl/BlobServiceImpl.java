package ga.rugal.gracker.core.service.impl;

import ga.rugal.gracker.core.dao.BlobDao;
import ga.rugal.gracker.core.service.BlobService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for blob service.
 *
 * @author Rugal Bernstein
 */
@Service
public class BlobServiceImpl implements BlobService {

  @Autowired
  @Getter
  private BlobDao dao;
}
