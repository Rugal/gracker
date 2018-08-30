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
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.shell.ExitRequest;

/**
 * Java based application context configuration class. <BR>
 * Including data source and transaction manager configuration.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@ComponentScan(basePackageClasses = {ga.PackageInfo.class,
                                     ga.rugal.gracker.shell.PackageInfo.class})
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

  /**
   * Exit if there is exception.
   *
   * @return
   */
  @Bean
  public ExitCodeExceptionMapper exitCodeExceptionMapper() {
    return exception -> {
      Throwable e = exception;
      while (e != null && !(e instanceof ExitRequest)) {
        e = e.getCause();
      }
      return e == null ? 1 : ((ExitRequest) e).status();
    };
  }
}
