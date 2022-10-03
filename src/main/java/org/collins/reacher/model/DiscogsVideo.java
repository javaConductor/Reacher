package org.collins.reacher.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Setter
@Getter
public class DiscogsVideo {
  URI uri;
  String title;
  String description;
  int duration;
  Boolean embed;
}
