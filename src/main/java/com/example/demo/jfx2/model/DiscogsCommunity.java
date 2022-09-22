package com.example.demo.jfx2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiscogsCommunity {
  int have;
  int want;
  DiscogsRating rating;
  DiscogsSubmitter submitter;
  List<DiscogsContributor> contributors;
  @JsonProperty("data_quality")
  String dataQuality;
  String status;
}
