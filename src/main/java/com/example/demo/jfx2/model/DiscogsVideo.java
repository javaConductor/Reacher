package com.example.demo.jfx2.model;

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
