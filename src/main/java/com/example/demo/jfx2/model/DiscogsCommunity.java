package com.example.demo.jfx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscogsCommunity {
  int have;
  int want;
  DiscogsRating rating;
  DiscogsSubmitter subbitter;
  List<DiscogsContributor> contributors;
  @JsonProperty("data_quality")
  String dataQuality;
  String status;
}
