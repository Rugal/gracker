package ga.rugal.gracker.core.service.impl;

import java.io.IOException;
import java.util.Arrays;

import ga.rugal.UnitTestBase;
import ga.rugal.gracker.core.dao.BlobDao;

import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class BlobServiceImplTest extends UnitTestBase {

  private static final String CONTENT = "content";

  @Autowired
  private BlobServiceImpl service;

  @Autowired
  private ObjectLoader objectLoader;

  @Mock
  private BlobDao dao;

  @Mock
  private ObjectId id;

  @Before
  @SneakyThrows
  public void setUp() {
    this.service.setDao(this.dao);

    BDDMockito.given(this.dao.create(BDDMockito.any())).willReturn(this.id);

    BDDMockito.given(this.dao.read(BDDMockito.any())).willReturn(this.objectLoader);
  }

  @SneakyThrows
  @Test
  public void title() {
    this.service.title(CONTENT);

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).create(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void body() {
    this.service.body(CONTENT);

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).create(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void label() {
    this.service.label(Arrays.asList(CONTENT));

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).create(BDDMockito.any());
  }

  @SneakyThrows
  @Test(expected = IOException.class)
  public void body_IOException() {
    BDDMockito.given(this.dao.create(BDDMockito.any())).willThrow(IOException.class);

    this.service.body(CONTENT);

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).create(BDDMockito.any());
  }

  @SneakyThrows
  @Test(expected = IOException.class)
  public void title_IOException() {
    BDDMockito.given(this.dao.create(BDDMockito.any())).willThrow(IOException.class);

    this.service.title(CONTENT);

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).create(BDDMockito.any());
  }

  @SneakyThrows
  @Test(expected = IOException.class)
  public void label_IOException() {
    BDDMockito.given(this.dao.create(BDDMockito.any())).willThrow(IOException.class);

    this.service.label(Arrays.asList(CONTENT));

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).create(BDDMockito.any());
  }

  @SneakyThrows
  @Test
  public void read() {
    this.service.read(this.id);

    BDDMockito.then(this.dao).should(BDDMockito.times(1)).read(BDDMockito.any());
  }
}
