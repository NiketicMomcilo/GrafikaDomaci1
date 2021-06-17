package sample.Levels;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import sample.Targets.Target;
import sample.Targets.TargetThirdLevel;
import sample.util.Main;
import sample.util.Score;

public class ThirdLevel extends BaseLevel {

    private static final int TARGETS = 20;
    private static final int BULLETS = 25;
    private int lastScore;
    private static boolean hit = false;

    public static Path path = buildPath();

    public static Path buildPath ( ) {
        Path path = new Path(new MoveTo(0, 0));
        int J_MAX = 180;
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
                new Translate(WIDTH / 2., HEIGHT / 2.),
                new Scale(3.3, 3.3)
        );
        return path;
    }


    public ThirdLevel ( Parent parent, double width, double height ) {
        super(parent, width, height, TARGETS, BULLETS,3);
        lastScore = Score.getScoreRef ().getScore ();
        buildLevel(buildSceneGroup());

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if ( this.hit == true && Score.getScoreRef().getTargetsLeft()>0) {
                //System.out.println("aaa");
                this.hit = false;
                Target target = new TargetThirdLevel();
                double duration = getRandomBetween(15, 20);
                PathTransition pathTransition1 = new PathTransition(Duration.seconds(duration), path, target);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), target);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0);
                fadeTransition.setInterpolator(Interpolator.EASE_IN);

                pathTransition1.play();
                fadeTransition.play();
                ( ( Group ) this.getRoot() ).getChildren().addAll(target);
            }
            if ( Score.getScoreRef().getTargetsHit() == TARGETS ) {
                goToNextLevel();
            }
        });

    }

    public static void setHit ( boolean h ) {
        hit = h;
    }

    @Override
    public Group buildSceneGroup ( ) {
        Group group = new Group();
        //group.getTransforms().addAll(new Scale(0.8, 0.8));

        for ( int i = 0 ; i < 5 ; i++ ) {

            Target target = new TargetThirdLevel();
            double duration = getRandomBetween(15, 20);
            PathTransition pathTransition1 = new PathTransition(Duration.seconds(duration), path, target);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), target);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0);
            fadeTransition.setInterpolator(Interpolator.EASE_IN);

            pathTransition1.play();
            fadeTransition.play();
            group.getChildren().add(target);
        }

        //group.getChildren().add(path);
        return group;
    }

    @Override
    public void goToNextLevel ( ) {
        FinalScreenLevel finalScreenLevel = new FinalScreenLevel(new Group(), WIDTH, HEIGHT, 0, 0);
        Score.getScoreRef().setTargetsHit(0);
        Main.stage.setScene(finalScreenLevel);
    }

    @Override
    public void repeatLevel ( ) {
        Score.getScoreRef().setScore(lastScore);
        Score.getScoreRef().setTargetsHit(0);
        ThirdLevel thirdLevel = new ThirdLevel(new Group(), WIDTH, HEIGHT);
        Main.stage.setScene(thirdLevel);
    }

    @Override
    public Rectangle setBackground ( ) {

        return null;
    }

    @Override
    public Text getLevelSignature ( ) {
        return new Text("Third level");
    }
}
