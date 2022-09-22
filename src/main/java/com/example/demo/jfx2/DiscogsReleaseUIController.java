package com.example.demo.jfx2;

import com.example.demo.jfx2.model.DiscogsArtist;
import com.example.demo.jfx2.model.DiscogsReleaseTrack;
import com.example.demo.jfx2.model.DiscogsReleasesResponse;
import com.example.demo.jfx2.services.DiscogsService;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class DiscogsReleaseUIController {

  private final HostServices hostServices;
  private final DiscogsService discogsService;

  @FXML
  public GridPane gridTrackList;
  @FXML
  public Button btnSearch;
  @FXML
  public TextArea textSearchReleaseId;
  @FXML
  public Text textReleaseId;
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
  Label errorMessage;

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
//    this.errorMessage.setStyle("");
    this.textSearchReleaseId.setDisable(false);
    this.textArtist.setDisable(false);
    this.textTitle.setDisable(false);
    this.textYear.setDisable(false);
    styleButton(btnSearch);

// and setCellFactory
    Callback fn = new Callback<TableColumn<DiscogsReleaseTrack, List<DiscogsArtist>>, TableCell<DiscogsReleaseTrack, List<DiscogsArtist>>>() {

      @Override
      public TableCell<DiscogsReleaseTrack, List<DiscogsArtist>> call(TableColumn<DiscogsReleaseTrack, List<DiscogsArtist>> param) {
        return new TableCell<DiscogsReleaseTrack, List<DiscogsArtist>>() {
          @Override
          protected void updateItem(List<DiscogsArtist> item, boolean empty) {

            //super.updateItem(item, empty);
            if (empty || item == null) {
              if (!empty)
                setText("nothing");
              setGraphic(null);
            } else {
              setText(item.toString());
            }
          }
        };
      }
    };
    artistsColumn.setCellFactory((Callback<TableColumn<DiscogsReleaseTrack, List<DiscogsArtist>>, TableCell<DiscogsReleaseTrack, List<DiscogsArtist>>>)fn);

  }

  void handleSearch(ActionEvent actionEvent) {
    String searchText = StringUtils.trim(this.textSearchReleaseId.getText());
    try {
      if (StringUtils.isNumeric(searchText)) {
        DiscogsReleasesResponse response = discogsService.getReleaseInfo(Integer.decode(searchText));
        nodePopulator.populate(gridTrackList, response);
      }
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }

  private void showError(String message) {
    this.errorMessage.setText(message);
  }

  void styleButton(Button btn){
    InnerShadow is = new InnerShadow();
    is.setOffsetX(4.0f);
    is.setOffsetY(4.0f);

    btn.setEffect(is);
    btn.setTextAlignment(TextAlignment.CENTER);
    btn.setFont(Font.font(null, FontWeight.BOLD, 20));
  }

}
