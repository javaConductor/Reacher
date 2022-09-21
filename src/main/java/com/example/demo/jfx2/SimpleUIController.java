package com.example.demo.jfx2;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SimpleUIController {

  private final Node discogsRelease;
  @FXML
  public VBox root;
  @FXML
  public Label label;
  @FXML
  public Button btnDiscogsReleases;
  @FXML
  public VBox content;

  public SimpleUIController(ApplicationContext context, Node discogsRelease ) {
    this.discogsRelease = discogsRelease;
  }

  @FXML
  public void initialize() {
      this.btnDiscogsReleases.setOnAction(actionEvent -> {
        this.content.getChildren().removeAll();
        this.content.getChildren().add(discogsRelease);
    });
  }
}
