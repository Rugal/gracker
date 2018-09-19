package config;

import java.util.Random;

import ga.rugal.PackageInfo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Rugal Bernstein
 */
@ComponentScan(basePackageClasses = PackageInfo.class)
@Configuration
public class UnitTestApplicationContext {

  @Bean
  @ConditionalOnMissingBean
  public Random random() {
    return Mockito.mock(Random.class);
  }

  @Bean
  @Scope("prototype")
  public Repository repository() {
    return Mockito.mock(Repository.class);
  }

  @Bean
  @Scope("prototype")
  public RefDatabase refDatabase() {
    return Mockito.mock(RefDatabase.class);
  }

  @Bean
  @Scope("prototype")
  public Git git() {
    return Mockito.mock(Git.class);
  }
}
