package com.example.demo.jfx2.services;

import com.example.demo.jfx2.JavaFxApplication;
import com.example.demo.jfx2.model.DiscogsReleasesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class DiscogsService {
  public DiscogsService() {

  }

  public DiscogsReleasesResponse getReleaseInfo(int releaseId) throws Exception {
    URL url = DiscogsService.class.getResource("/discogs-releases-phreek.json");
    if (url == null) {
      throw new IllegalStateException(String.format("no such file: [discogs-releases-phreek.json]"));
    }

    DiscogsReleasesResponse discogsReleasesResponse = new ObjectMapper().readValue( new File(url.toURI()), DiscogsReleasesResponse.class );
    return discogsReleasesResponse;
  }
}
