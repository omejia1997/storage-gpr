package ec.edu.espe.gpr.storage.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
  private static final int CONNECT_TIMEOUT = 5_000;
  private static final int READ_TIMEOUT = 5_000;
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
   // Do any additional configuration here
   return builder.build();
  }
}
