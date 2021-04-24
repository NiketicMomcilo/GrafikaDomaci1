package sample.Levels;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import sample.Targets.Target;

public class TestLevel extends BaseLevel {

    public TestLevel (Parent parent, double width, double height, int numOfTargets, int numberOfBullets) {
        super(parent, width, height, numOfTargets, numberOfBullets);
        buildLevel(buildSceneGroup());
    }

    @Override
    public Group buildSceneGroup() {
        Group root = new Group();

        Target t1 = new Target();
        Translate t = new Translate();
        Translate translate3 = new Translate();
        t1.getTransforms().addAll(
                t
        );

        Target t2 = new Target();
        t2.getTransforms().addAll(
                translate3
        );

        Target t3 = new Target();
        t3.getTransforms().addAll(
                new Translate(WIDTH / 5, HEIGHT / 5)
        );


        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(t.xProperty(), 0, Interpolator.LINEAR),
                        new KeyValue(t.yProperty(), 0, Interpolator.LINEAR)


                ),
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(t.xProperty(), WIDTH, Interpolator.LINEAR),
                        new KeyValue(t.yProperty(), HEIGHT, Interpolator.LINEAR)
                )
        );
        t1.getTimelines().add(timeline);

        Timeline timeline1 = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(translate3.xProperty(), 0, Interpolator.LINEAR),
                        new KeyValue(translate3.yProperty(), HEIGHT, Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(6),
                        new KeyValue(translate3.xProperty(), WIDTH, Interpolator.LINEAR),
                        new KeyValue(translate3.yProperty(), 0, Interpolator.LINEAR)
                )
        );

        t2.getTimelines().add(timeline1);

        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        timeline1.setAutoReverse(true);
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();

        root.getChildren().addAll(t1);
        root.getChildren().addAll(t2);
        root.getChildren().addAll(t3);

        return root;
    }

    @Override
    public void goToNextLevel ( ) {

    }

    @Override
    public void repeatLevel ( ) {

    }

    @Override
    public Text getLevelSignature ( ) {
        Text text = new Text("Test");
        return text;
    }
}
