package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.GLWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    GLWindow window = new GLWindow(800, 450);
    window.setPixels(50, 50, 200, 200, Color.GREEN);
    window.write();
    Scene scene = new Scene(new Pane(window));

    primaryStage.setScene(scene);
    primaryStage.setTitle("GLWindow");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
