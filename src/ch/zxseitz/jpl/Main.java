package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.fx.GLWindow;
import ch.zxseitz.jpl.framework.color.HdrColor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    var window = new GLWindow(800, 450);
    window.setPixels(50, 50, 200, 200, HdrColor.RED);
    window.write();
    var scene = new Scene(window);

    primaryStage.setScene(scene);
    primaryStage.setTitle("GLWindow");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
