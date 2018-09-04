package config;

import java.util.Random;

import ga.PackageInfo;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.ParameterResolver;
import org.springframework.shell.Shell;

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
  @ConditionalOnMissingBean
  public ParameterResolver parameterResolver() {
    return Mockito.mock(ParameterResolver.class);
  }

  @Bean
  @ConditionalOnMissingBean
  public Shell shell() {
    return Mockito.mock(Shell.class);
  }
}
