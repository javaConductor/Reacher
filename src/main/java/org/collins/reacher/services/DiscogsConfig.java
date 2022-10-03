package org.collins.reacher.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Configuration
public class DiscogsConfig {

  @Bean
  public RestTemplate templateDiscogsReleases(@Value("${reacher-sources.discogs.user}") String discogsUser,
                                              @Value("${reacher-sources.discogs.token}") String discogsToken,
                                              @Value("${reacher-sources.discogs.url}") String discogsUrl,
                                              ObjectMapper objectMapper){
    RestTemplate restTemplate = new RestTemplateBuilder()
      .basicAuthentication(discogsUser, discogsToken)
      .rootUri(discogsUrl)
      .build();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);
    restTemplate.getMessageConverters().add(converter);
    return restTemplate;
  }

  @Bean
  public DiscogsService discogsService(ObjectMapper objectMapper, RestTemplate templateDiscogsReleases, File releasesDirectory) {
    return new DiscogsService(objectMapper, templateDiscogsReleases, releasesDirectory);
  }

  @Bean
  public File localRepositoryDirectory(@Value("${reacher-sources.discogs.local_repository}") String discogsDirectory){
    return new File(discogsDirectory);
  }

  @Bean
  public File masterReleasesDirectory(File localRepositoryDirectory){
    return new File(localRepositoryDirectory, "master-releases");
  }


  @Bean
  public File releasesDirectory(File localRepositoryDirectory){
    return new File(localRepositoryDirectory, "releases");
  }

  @Bean
  public File artistsDirectory(File localRepositoryDirectory){
    return new File(localRepositoryDirectory, "artists");
  }
}
