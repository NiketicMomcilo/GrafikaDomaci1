package sample.Levels;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import sample.Targets.Target;
import sample.Targets.TargetSecondLevel;
import sample.Targets.TargetThirdLevel;
import sample.util.Main;
import sample.util.Score;

import java.util.Collection;

public class ThirdLevel extends BaseLevel {

    private static final int TARGETS = 20;
    private static final int BULLETS = 25;
    private int lastScore;

    public static Path path = buildPath();

    public static Path buildPath ( ) {
        Path path = new Path(new MoveTo(0, 0));
        int J_MAX = 120;
        for ( int i = 0 ; i < 1 ; i++ ) {
            for ( int j = 0 ; j < J_MAX ; j++ ) {
                double x1 = ( i * J_MAX + j ) * Math.cos(( double ) 6 * j);
                double y1 = ( i * J_MAX + j ) * Math.sin(( double ) 6 * j);
                path.getElements().add(
                        new LineTo(x1, y1)
                );
            }
        }

        path.setStrokeWidth(2);
        path.getTransforms().addAll(
                new Translate(WIDTH / 2, HEIGHT / 2),
                new Scale(3.3, 3.3)
        );
        return path;
    }




    public ThirdLevel ( Parent parent, double width, double height ) {
        super(parent, width, height, TARGETS, BULLETS);
        buildLevel(buildSceneGroup());

    }

    @Override
    public Group buildSceneGroup ( ) {
        Group group = new Group();


        for ( int i = 0 ; i < 5 ; i++ ) {

            Target target = new TargetSecondLevel();
            double duration = getRandomBetween(5,15);
            PathTransition pathTransition1 = new PathTransition(Duration.seconds(duration), path, target);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), target);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0);
            fadeTransition.setInterpolator(Interpolator.EASE_BOTH);

            pathTransition1.play();
            fadeTransition.play();
            group.getChildren().add(target);
        }

        group.getChildren().add(path);
        return group;
    }

    @Override
    public void goToNextLevel ( ) {

    }

    @Override
    public void repeatLevel ( ) {
        Score.getScoreRef().setScore(lastScore);
        Score.getScoreRef().setTargetsHit(0);
        ThirdLevel thirdLevel = new ThirdLevel(new Group(), WIDTH, HEIGHT);
        Main.stage.setScene(thirdLevel);
    }

    @Override
    public Text getLevelSignature ( ) {
        Text text = new Text("Level Three");
        return text;
    }
}
