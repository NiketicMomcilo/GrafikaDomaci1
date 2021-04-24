package sample.Levels;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class ThirdLevel extends BaseLevel {

    private static final int TARGETS = 20;
    private static final int BULLETS = 25;
    private int lastScore;

    public ThirdLevel ( Parent parent, double width, double height) {
        super(parent, width, height, TARGETS, BULLETS);
        buildLevel (buildSceneGroup ());
    }

    @Override
    Group buildSceneGroup ( ) {
        Group group = new Group();
        Text text = new Text("WELCOME TO LEVEL 3");
        text.getTransforms().addAll(
                new Translate(WIDTH/2, HEIGHT/2),
                new Scale(3,3)
        );
        group.getChildren().addAll(text);
        return group;
    }

    @Override
    void goToNextLevel ( ) {

    }

    @Override
    void repeatLevel ( ) {

    }
}
