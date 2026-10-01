package org.example.loldemoserver.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "riot")
@Getter
@Setter
@NoArgsConstructor
public class RiotApiConfig {

      //private String apiKey;
      private String dataDragonBaseUrl;
      private String dataSplashBaseUrl;
      private String apiDefaultVersion;
      private String apiDefaultLanguage;

}
