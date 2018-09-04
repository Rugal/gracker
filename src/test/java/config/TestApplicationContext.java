package config;

import ga.rugal.gracker.util.StringUtil;

import com.google.gson.Gson;
import org.eclipse.jgit.lib.PersonIdent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Rugal Bernstein
 */
@Configuration
public class TestApplicationContext {

  @Bean
  @ConditionalOnMissingBean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public byte[] testData() {
    return StringUtil.getByte("Rugal Bernstein");
  }

  @Bean
  @ConditionalOnMissingBean
  public PersonIdent personIdent() {
    return new PersonIdent("Rugal Bernstein", "test@mail.com");
  }
}
