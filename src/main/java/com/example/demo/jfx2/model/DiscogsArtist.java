package com.example.demo.jfx2.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class DiscogsArtist {
  String name;
  String anv, join, role, tracks;
  long id;
  URL resource_url;
}
