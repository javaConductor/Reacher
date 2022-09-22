package com.example.demo.jfx2.config;

import com.example.demo.jfx2.JavaFxApplication;
import com.example.demo.jfx2.NodePopulator;
import com.example.demo.jfx2.model.DiscogsReleaseTrack;
import com.example.demo.jfx2.model.DiscogsReleasesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class UIConfig {
  public static String FXML_UI = "/ui.fxml";
  public static String DISCOGS_RELEASE = "/discogs-release.fxml";

  @Autowired
  ApplicationContext ac;

  @Bean
  @Qualifier("main")
  public Node mainScene() throws Exception {
    return loadFxml(FXML_UI);
  }

  @Bean
  @Qualifier("discogs-release")
  @Scope("prototype")
  public Node discogsRelease() throws Exception {
    return loadFxml(DISCOGS_RELEASE);
  }


  @Bean
  NodePopulator<TableView, java.util.List<DiscogsReleaseTrack>> trackListPopulator() {

    return new NodePopulator<TableView, java.util.List<DiscogsReleaseTrack>>() {
      @Override
      public Node populate(TableView node, List<DiscogsReleaseTrack> data) {
        ObservableListWrapper<DiscogsReleaseTrack> olw = new ObservableListWrapper<DiscogsReleaseTrack>(data);
        //olw.addAll(data);
        node.setItems(olw);
        return node;
      }
    };
  }

  @Bean
  public NodePopulator<GridPane, DiscogsReleasesResponse> nodePopulator(NodePopulator<TableView, java.util.List<DiscogsReleaseTrack>> trackListPopulator) {

    return new NodePopulator<GridPane, DiscogsReleasesResponse>() {
      @Override
      public Node populate(GridPane node, DiscogsReleasesResponse data) {
        node.getChildren().stream()
          .filter(child -> child.getId() != null)
          .forEach(child -> {

            switch (child.getId()) {

              case "textReleaseId":
                ((Text) child).setText("" + data.getId());
                break;

              case "textArtist":
                ((Text) child).setText("" + data.getArtistsSort());
                break;

              case "textTitle":
                ((Text) child).setText("" + data.getTitle());
                break;

              case "textYear":
                ((Text) child).setText("" + data.getYear());
                break;


              case "textCountry":
                ((Text) child).setText("" + data.getCountry());
                break;


              case "textGenres":
                ((Text) child).setText("" + data.getGenres().toString());
                break;


              case "textStyles":
                ((Text) child).setText("" + data.getStyles().toString());
                break;

              case "trackListTable":
                trackListPopulator.populate((TableView) child, data.getTracklist());
                break;

              default:
                break;
            }
          });
        return node;
      }
    };
  }

  @Bean
  public ObjectMapper objectMapper(){
    ObjectMapper mapper = new ObjectMapper();
    JavaTimeModule javaTimeModule=new JavaTimeModule();
    // Hack time module to allow 'Z' at the end of string (i.e. javascript json's)
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
    mapper.registerModule(javaTimeModule);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return mapper;

  }

  public Node loadFxml(String fxmlFile) throws Exception {
    URL url = JavaFxApplication.class.getResource(fxmlFile);
    if (url == null) {
      throw new IllegalStateException(String.format("fxml definition: [%s] not found", FXML_UI));
    }
    FXMLLoader fxmlLoader = new FXMLLoader(url, null, new JavaFXBuilderFactory(), null,
      Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
    fxmlLoader.setControllerFactory(ac::getBean);

    //load scene
    return fxmlLoader.load();
  }
}
