package org.collins.reacher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.URL;

@Getter
@Setter
public class DiscogsImage {
  String type;
  URI uri;
  @JsonProperty("resource_url")
  URL resourceUrl;
  URI uri150;
  int width, height;

}
