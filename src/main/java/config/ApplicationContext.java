package config;

import java.io.File;
import java.util.Random;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Java based application context configuration class. <BR>
 * Including data source and transaction manager configuration.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@ComponentScan
@Configuration
@Slf4j
public class ApplicationContext {

  /**
   * Placeholder configurer.
   *
   * @return bean
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /**
   * Gson.
   *
   * @return bean
   */
  @Bean
  public Gson gson() {
    return new Gson();
  }

  /**
   * Random generator.
   *
   * @return bean
   */
  @Bean
  public Random random() {
    return new Random();
  }

  /**
   * Repository.
   *
   * @return
   */
  @Bean
  @SneakyThrows
  public Repository repository() {
    final FileRepositoryBuilder builder = new FileRepositoryBuilder();
    builder.setGitDir(new File(".git"));
    return builder.build();
  }

  /**
   * Reference database.
   *
   * @param repository
   *
   * @return
   */
  @Bean
  public RefDatabase refDatabase(final Repository repository) {
    return repository.getRefDatabase();
  }

  @Bean
  public PersonIdent personIdent() {
    return new PersonIdent("Rugal Bernstein", "test@mail.com");
  }
}
