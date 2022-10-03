package org.collins.reacher.services;

import org.collins.reacher.model.DiscogsReleasesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
public class DiscogsService {
  final RestTemplate templateDiscogsReleases;
  final ObjectMapper objectMapper;
  private final File releasesDirectory;

  public DiscogsService(ObjectMapper objectMapper,
                        RestTemplate templateDiscogsReleases,
                        File releasesDirectory) {
    this.objectMapper = objectMapper;
    this.templateDiscogsReleases = templateDiscogsReleases;
    this.releasesDirectory = releasesDirectory;
  }

  public DiscogsReleasesResponse getReleaseInfo(int releaseId) throws Exception {
    ResponseEntity<String> response = templateDiscogsReleases.getForEntity(
      "/releases/" + releaseId, String.class);
    log.debug("DISCOGS/releases/" + releaseId + " " + response.getBody());
    return objectMapper.readValue(response.getBody(), DiscogsReleasesResponse.class);
  }


  public File saveRelease(@NonNull DiscogsReleasesResponse releasesResponse) throws Exception {

    if (releasesResponse.getReleased() == null) {
      return null;
    }

    String releaseId = "" + releasesResponse.getId();
    File f = new File(this.releasesDirectory, releaseId + ".json");
    String responseJson = this.objectMapper.writeValueAsString(releasesResponse);
    Files.write(f.toPath(), responseJson.getBytes());
    return f;
  }

  public Optional<DiscogsReleasesResponse> readRelease(@NonNull long releaseId) throws Exception {
    String releaseIdStr = "" + releaseId;
    File f = new File(this.releasesDirectory, releaseIdStr + ".json");
    String releaseJson = Files.readString(f.toPath());
    DiscogsReleasesResponse release = this.objectMapper.readValue(
                                                              releaseJson,
                                                              DiscogsReleasesResponse.class);
    return release == null ? Optional.empty() : Optional.of(release);
  }


}
