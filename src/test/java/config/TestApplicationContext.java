package config;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Rugal Bernstein
 */
@Configuration
public class TestApplicationContext {

  @ConditionalOnMissingBean
  @Bean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  @SneakyThrows
  public byte[] testData() {
    return "Rugal Bernstein".getBytes(SystemDefaultProperty.ENCODE);
  }
}
