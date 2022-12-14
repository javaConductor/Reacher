package org.collins.reacher.model;

import org.collins.reacher.model.converters.LocalDateTimeToStringConverter;
import org.collins.reacher.model.converters.StringToLocalDatetimeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiscogsReleasesResponse {
  long id;
  String status;
  int year;

  @JsonProperty("resource_url")
  URL resourceUrl;

  @JsonProperty("uri")
  URI uri;
  List<DiscogsArtist> artists;

  @JsonProperty("artists_sort")
  String artistsSort;

  List<DiscogsLabel> labels;
  List<DiscogsSeries> series;
  List<DiscogsCompany> companies;
  List<DiscogsFormat> formats;

  @JsonProperty("data_quality")
  String dataQuality;
  DiscogsCommunity community;

  @JsonProperty("format_quantity")
  int formatQuantity;

  @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
  @JsonDeserialize(converter = StringToLocalDatetimeConverter.class)
  @JsonProperty("date_added")
  LocalDateTime dateAdded;

  @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
  @JsonDeserialize(converter = StringToLocalDatetimeConverter.class)
  @JsonProperty("date_changed")
  LocalDateTime dateChanged;

  @JsonProperty("num_for_sale")
  int numberForSale;

  @JsonProperty("lowest_price")
  Double lowestPrice;

  @JsonProperty("master_id")
  int masterId;

  @JsonProperty("master_url")
  URL masterUrl;

  String title;
  String country;
  String released; //1987-07-00
  String notes;

  @JsonProperty("released_formatted")
  String releasedFormatted;
  List<DiscogsIdentifier> identifiers;
  List<DiscogsVideo> videos;
  List<String> genres;
  List<String> styles;
  List<DiscogsReleaseTrack> tracklist;

  @JsonProperty("extraartists")
  List<DiscogsArtist> extraArtists;
  List<DiscogsImage> images;
  String thumb;

  @JsonProperty("estimated_weight")
  int estimateWeight;

  @JsonProperty("blocked_from_sale")
  Boolean blockedFromSale;
}
