package ga.rugal.gracker.core.service.impl;

import java.io.IOException;

import ga.rugal.gracker.core.dao.BlobDao;
import ga.rugal.gracker.core.service.BlobService;
import ga.rugal.gracker.util.StringUtil;

import lombok.Getter;
import org.eclipse.jgit.lib.ObjectId;
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
   * Create blob object for title.
   *
   * @param title input string
   *
   * @return blob
   *
   * @throws IOException unable to write to file system
   */
  public ObjectId title(final String title) throws IOException {
    return this.createBlob(title);
  }

  /**
   * Create blob object for body.
   *
   * @param body input string
   *
   * @return blob
   *
   * @throws IOException unable to write to file system
   */
  public ObjectId body(final String body) throws IOException {
    return this.createBlob(body);
  }

  /**
   * Create blob object for label.
   *
   * @param labels label array
   *
   * @return blob
   *
   * @throws IOException unable to write to file system
   */
  public ObjectId label(final String[] labels) throws IOException {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < labels.length; i++) {
      if (0 != i) {
        sb.append(",");
      }
      sb.append(labels[i]);
    }
    return this.createBlob(sb.toString());
  }
}
