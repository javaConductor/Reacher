package org.collins.reacher.config;

import org.collins.reacher.model.converters.LocalDateTimeToStringConverter;
import org.collins.reacher.model.converters.StringToLocalDatetimeConverter;
import org.springframework.context.annotation.Bean;

public class ModelConfig {

  @Bean
  LocalDateTimeToStringConverter localDateTimeToStringConverter(){
    return new LocalDateTimeToStringConverter();
  }

  @Bean
  StringToLocalDatetimeConverter stringToLocalDatetimeConverter(){
    return new StringToLocalDatetimeConverter();
  }

}
