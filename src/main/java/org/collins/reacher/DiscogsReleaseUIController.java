package org.collins.reacher;

import org.collins.reacher.model.DiscogsArtist;
import org.collins.reacher.model.DiscogsReleaseTrack;
import org.collins.reacher.model.DiscogsReleasesResponse;
import org.collins.reacher.services.DiscogsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DiscogsReleaseUIController {

  private final HostServices hostServices;
  private final DiscogsService discogsService;
  private final File releasesDirectory;
  final NodePopulator<GridPane, DiscogsReleasesResponse> nodePopulator;
  private DiscogsReleasesResponse currentResponse;
  private final ObjectMapper objectMapper;
  @FXML
  public GridPane gridTrackList;
  @FXML
  public Button btnSearch;
  @FXML
  public Button btnSave;
  @FXML
  public TextArea textSearchReleaseId;
  @FXML
  public Text textReleaseId;
  @FXML
  public Text textMasterReleaseId;
  @FXML
  public Text textArtist;
  @FXML
  public Text textTitle;
  @FXML
  public Text textYear;
  @FXML
  public TableView<DiscogsReleaseTrack> tableTrackList;

  @FXML
  public TableColumn<DiscogsReleaseTrack, List<DiscogsArtist>> artistsColumn;

  @FXML
  public TableColumn<DiscogsReleaseTrack, List<DiscogsArtist>> extraArtistsColumn;

  @FXML
  TextArea errorMessage;

  public DiscogsReleaseUIController(ApplicationContext context,
                                    DiscogsService discogsService,
                                    ObjectMapper objectMapper,
                                    NodePopulator<GridPane, DiscogsReleasesResponse> nodePopulator,
                                    File releasesDirectory) {
    this.hostServices = context.getBean(HostServices.class);
    this.discogsService = discogsService;
    this.nodePopulator = nodePopulator;
    this.releasesDirectory = releasesDirectory;
    this.objectMapper = objectMapper;
  }

  @FXML
  public void initialize() {
    this.btnSearch.setOnAction(this::handleSearch);
    this.btnSave.setOnAction(this::handleSave);
    this.textSearchReleaseId.setDisable(false);
    this.textArtist.setDisable(false);
    this.textTitle.setDisable(false);
    this.textYear.setDisable(false);
    styleButton(btnSearch);

    // and setCellFactory
    Callback<TableColumn<DiscogsReleaseTrack, List<DiscogsArtist>>, TableCell<DiscogsReleaseTrack, List<DiscogsArtist>>> fn = param -> new ArtistCell();
    artistsColumn.setCellFactory(fn);
    extraArtistsColumn.setCellFactory(fn);
  }

  void handleSearch(ActionEvent actionEvent) {
    String searchText = StringUtils.trim(this.textSearchReleaseId.getText());
    try {
      if (StringUtils.isNumeric(searchText)) {
        this.currentResponse = discogsService.getReleaseInfo(Integer.decode(searchText));
        nodePopulator.populate(gridTrackList, new DiscogsReleasesResponse());
        nodePopulator.populate(gridTrackList, this.currentResponse);
      }
    } catch (Exception e) {
      this.currentResponse = new DiscogsReleasesResponse();
      showError(e.getMessage());
    }
  }

  void handleSave(ActionEvent actionEvent) {
    String releaseId = StringUtils.trim(this.textReleaseId.getText());
    if (StringUtils.isEmpty(releaseId)) {
      return;
    }
    try {
      discogsService.saveRelease(this.currentResponse);
    } catch (Exception e) {
      log.debug("handleSave", e);
      showError(e.getMessage());
    }
  }

  private void showError(String message) {
    log.debug("Error: " + message);
    this.errorMessage.setText(message);
  }

  void styleButton(Button btn) {
    InnerShadow is = new InnerShadow();
    is.setOffsetX(4.0f);
    is.setOffsetY(4.0f);

    btn.setEffect(is);
    btn.setTextAlignment(TextAlignment.CENTER);
    btn.setFont(Font.font(null, FontWeight.BOLD, 20));
  }

  class ArtistCell extends TableCell<DiscogsReleaseTrack, List<DiscogsArtist>> {
    @Override
    protected void updateItem(List<DiscogsArtist> trackArtists, boolean empty) {

      super.updateItem(trackArtists, empty);
      if (empty || trackArtists == null) {
        if (!empty) {
          setText(null);
          setGraphic(null);
        }
      } else {
        String artistString = createArtistString(trackArtists);
        setText(artistString);
      }
    }

    private String createArtistString(List<DiscogsArtist> trackArtists) {
      Map<String, List<String>> roleData = new HashMap<>();
      trackArtists.forEach(discogsArtist -> {
        List<String> names = roleData.get(discogsArtist.getRole());
        if (names == null){
          names = new ArrayList<>();
        }
        names.add(discogsArtist.getName());
        roleData.put(discogsArtist.getRole(), names);
      });
      StringBuilder sb = new StringBuilder();
      roleData.forEach((role, nameList) -> {
        sb.append(role);
        sb.append(": ");
        String s = nameList.stream().collect(Collectors.joining(", "));
        sb.append(s);
        sb.append(" | ");
      } );
      return sb.toString();
    }
  }
}
