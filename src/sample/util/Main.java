
package sample.util;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import sample.Levels.BaseLevel;
import sample.Levels.FirstLevel;
import sample.Levels.SecondLevel;


public class Main extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 900;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ZoomaDeluxe");
        FirstLevel level = new FirstLevel (new Group(), WIDTH, HEIGHT);
        //SecondLevel secondLevel = new SecondLevel (new Group(), WIDTH, HEIGHT);

        stage = primaryStage;
        primaryStage.setScene(level);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

