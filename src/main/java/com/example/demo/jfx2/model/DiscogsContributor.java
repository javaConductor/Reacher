package com.example.demo.jfx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

public class DiscogsContributor {
  String username;
  @JsonProperty("resource_url")
  URL resourceUrl;
}
