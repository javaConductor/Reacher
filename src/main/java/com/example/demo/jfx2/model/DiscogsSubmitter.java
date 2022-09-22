package com.example.demo.jfx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class DiscogsSubmitter {
  String username;

  @JsonProperty("resource_url")
  URL resourceUrl;
}
