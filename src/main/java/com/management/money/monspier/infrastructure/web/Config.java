package com.management.money.monspier.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json
    .MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation
    .WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class Config extends WebMvcConfigurerAdapter {

  @Autowired
  private Environment env;

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    converters.add(0, new MappingJackson2HttpMessageConverter(mapper));
  }

  @Bean(initMethod = "migrate")
  @ConditionalOnProperty("flyway.enabled")
  public Flyway flyway(DataSource dataSource) {
    Flyway flyway = new Flyway();
    flyway.setBaselineOnMigrate(true);
    flyway.setLocations(env.getProperty("flyway.location"));
    flyway.setDataSource(dataSource);

    return flyway;
  }
}
