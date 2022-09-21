package com.example.demo.jfx2;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

  private final String applicationTitle;
  private final Node parent;
  private final ApplicationContext ac;

  @SneakyThrows
  StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
                ApplicationContext ac,
                @Qualifier("main") Node parent) {
    this.applicationTitle = applicationTitle;
    this.ac = ac;
    this.parent = parent;
  }

  @SneakyThrows
  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    Stage stage = event.getStage();

    //load scene
    Scene scene = new Scene((Parent) this.parent, 600, 600);

    // add scene to stage
    stage.setScene(scene);
    stage.setTitle(this.applicationTitle);
    stage.show();
  }

  private Object call(Class<?> aClass) {
    return ac.getBean(aClass);
  }
}
