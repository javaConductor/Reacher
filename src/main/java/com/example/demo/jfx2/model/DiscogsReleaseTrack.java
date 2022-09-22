package com.example.demo.jfx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class DiscogsReleaseTrack {
  String position; // "A"

  @JsonProperty("type_")
  String type;

  String title;
  String duration;

  @JsonProperty("extraartists")
  List<DiscogsArtist> extraArtists;

}
