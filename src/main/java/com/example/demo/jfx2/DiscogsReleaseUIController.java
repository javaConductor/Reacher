package com.example.demo.jfx2;

import com.example.demo.jfx2.model.DiscogsReleasesResponse;
import com.example.demo.jfx2.services.DiscogsService;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class DiscogsReleaseUIController {

  private final HostServices hostServices;
  private final DiscogsService discogsService;
  @FXML
  public GridPane gridTrackList;
  @FXML
  public Button btnSearch;
  @FXML
  public Text textSearchReleaseId;
  @FXML
  public Text textReleaseId;
  @FXML
  public Text textArtist;
  @FXML
  public Text textTitle;
  @FXML
  public Text textYear;
  @FXML
  public TableView<DiscogsReleasesResponse> tableTrackList;
  ApplicationContext context;
  @Autowired
  NodePopulator<GridPane, DiscogsReleasesResponse> nodePopulator;

  public DiscogsReleaseUIController(ApplicationContext context,
                                    DiscogsService discogsService) {
    this.hostServices = context.getBean(HostServices.class);
    this.discogsService = discogsService;
  }

  @FXML
  public void initialize() {

    this.btnSearch.setOnAction(this::handleSearch);

  }

  void handleSearch(ActionEvent actionEvent) {
    String searchText = StringUtils.trim(this.textSearchReleaseId.getText());
    if (StringUtils.isNumeric(searchText)) {
      DiscogsReleasesResponse response = discogsService.getReleaseInfo(Integer.decode(searchText));
      nodePopulator.populate(gridTrackList, response);
    }
  }
}
