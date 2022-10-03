package org.collins.reacher.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class DiscogsLabel {
  String name;
  String catno;
  String entity_type;
  String entity_type_name;
  long id;
  URL resource_url;
}
