package com.example.demo.jfx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscogsCompany {
  String name;
  String catno;
  String entity_type;
  String entity_type_name;
  long id;
  @JsonProperty("resource_url")
  String resourceUrl;
}
