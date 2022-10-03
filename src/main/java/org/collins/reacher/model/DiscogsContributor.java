package org.collins.reacher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class DiscogsContributor {
  String username;
  @JsonProperty("resource_url")
  URL resourceUrl;
}
