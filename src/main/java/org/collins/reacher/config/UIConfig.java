package org.collins.reacher.config;

import org.collins.reacher.JavaFxApplication;
import org.collins.reacher.NodePopulator;
import org.collins.reacher.model.DiscogsReleaseTrack;
import org.collins.reacher.model.DiscogsReleasesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
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
        node.setItems(FXCollections.observableList(data==null ? List.of( ) : data));
        node.refresh();
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


              case "textMasterReleaseId":
                ((Text) child).setText("" + data.getMasterId());
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
                ((Text) child).setText(data.getCountry());
                break;


              case "textGenres":
                if (data.getGenres() == null)
                  ((Text) child).setText("");
                else
                  ((Text) child).setText(data.getGenres().toString());
                break;


              case "textStyles":
                if (data.getStyles() == null)
                  ((Text) child).setText("");
                else
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
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
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
