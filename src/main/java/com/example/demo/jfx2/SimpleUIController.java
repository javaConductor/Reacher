package com.example.demo.jfx2;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SimpleUIController {

  private final Node discogsRelease;
  @FXML
  public VBox root;
  @FXML
  public Text textTitle;
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
        this.content.getChildren().retainAll();
        this.content.getChildren().add(discogsRelease);
    });

      styleButton(btnDiscogsReleases);
      styleTitle(textTitle);
  }

  void styleButton(Button btn){
    InnerShadow is = new InnerShadow();
    is.setOffsetX(4.0f);
    is.setOffsetY(4.0f);

    btn.setEffect(is);
    btn.setTextAlignment(TextAlignment.CENTER);
    btn.setFont(Font.font(null, FontWeight.BOLD, 20));
  }

  void styleTitle(Text textTitle) {
    Blend blend = new Blend();
    blend.setMode(BlendMode.MULTIPLY);

    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(254, 235, 66, 0.3));
    ds.setOffsetX(5);
    ds.setOffsetY(5);
    ds.setRadius(5);
    ds.setSpread(0.2);

    blend.setBottomInput(ds);

    DropShadow ds1 = new DropShadow();
    ds1.setColor(Color.web("#f13a00"));
    ds1.setRadius(20);
    ds1.setSpread(0.2);

    Blend blend2 = new Blend();
    blend2.setMode(BlendMode.MULTIPLY);

    InnerShadow is = new InnerShadow();
    is.setColor(Color.web("#feeb42"));
    is.setRadius(9);
    is.setChoke(0.8);
    blend2.setBottomInput(is);

    InnerShadow is1 = new InnerShadow();
    is1.setColor(Color.web("#f13a00"));
    is1.setRadius(5);
    is1.setChoke(0.4);
    blend2.setTopInput(is1);

    Blend blend1 = new Blend();
    blend1.setMode(BlendMode.MULTIPLY);
    blend1.setBottomInput(ds1);
    blend1.setTopInput(blend2);

    blend.setTopInput(blend1);

    textTitle.setFont(Font.font(null, FontWeight.BOLD, 30));
    textTitle.setEffect(blend);
  }
}
