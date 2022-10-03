package org.collins.reacher.model.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocalDateTimeToStringConverter extends StdConverter<LocalDateTime, String> {
  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  @Override
  public String convert(LocalDateTime value) {
    return value.format(DATE_FORMATTER);
  }
}
