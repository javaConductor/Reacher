package org.collins.reacher.model.converters;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.LocalDateTime;

public class StringToLocalDatetimeConverter extends StdConverter<String, LocalDateTime> {

  @Override
  public LocalDateTime convert(String value) {
    String s = value.substring(0,19);
    return LocalDateTime.parse(s, LocalDateTimeToStringConverter.DATE_FORMATTER);
  }
}
