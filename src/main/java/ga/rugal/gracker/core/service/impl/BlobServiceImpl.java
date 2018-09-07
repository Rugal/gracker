package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.List;

import config.SystemDefaultProperty;

import ga.rugal.gracker.core.dao.BlobDao;
import ga.rugal.gracker.core.service.BlobService;
import ga.rugal.gracker.util.StringUtil;

import lombok.Getter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
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

  private ObjectId createBlob(final String content) throws IOException {
    return this.dao.create(StringUtil.getByte(content.trim()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectId title(final String title) throws IOException {
    return this.createBlob(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectId body(final String body) throws IOException {
    return this.createBlob(body);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectId label(final List<String> label) throws IOException {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < label.size(); i++) {
      if (0 != i) {
        sb.append(",");
      }
      sb.append(label.get(i));
    }
    return this.createBlob(sb.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String read(final ObjectId blobId) throws IOException {
    final ObjectLoader loader = this.dao.read(blobId);
    return new String(loader.getBytes(), SystemDefaultProperty.ENCODE);
  }
}
