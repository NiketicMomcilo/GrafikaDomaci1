package sample.Levels;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import sample.Targets.TargetSecondLevel;
import sample.util.Main;
import sample.util.Score;
import sample.Targets.Target;

public class SecondLevel extends BaseLevel {

    public static boolean[] track = { false, false, false, false };
    public static final int[] offset = { MAX_CIRCLE_WIDTH, 3 * MAX_CIRCLE_WIDTH, 5 * MAX_CIRCLE_WIDTH, 7 * MAX_CIRCLE_WIDTH };
    private static final int TARGETS = 20;
    private static final int BULLETS = 25;
    private int lastScore;

    public SecondLevel (Parent parent, double width, double height) {
        super (parent, width, height, TARGETS, BULLETS,2);
        this.setCursor(getCustomImageCursor());
        for ( boolean b : track ) {
            b = false;
        }
        lastScore = Score.getScoreRef ().getScore ();
        buildLevel (buildSceneGroup ());


        this.addEventHandler (MouseEvent.MOUSE_PRESSED, mouseEvent -> {

            if ( Score.getScoreRef ().getTargetsLeft () > 0 ) {
                for ( int i = 0 ; i < 4 ; i++ ) {
                    if ( track[ i ] ) {
                        track[i] = false;
                        Target target = new TargetSecondLevel ();
                        Translate initialTranslate = new Translate (offset[i], 0);
                        Rotate rotate = new Rotate ();
                        Translate finalTranslate = new Translate (WIDTH / 2., HEIGHT / 2.);
                        target.getTransforms ().addAll (
                                finalTranslate,
                                rotate,
                                new Scale (0.55, 0.55),
                                initialTranslate
                        );
                        Timeline timeline = new Timeline (
                                new KeyFrame (
                                        Duration.ZERO,
                                        new KeyValue (rotate.angleProperty (), 0, Interpolator.LINEAR)
                                ),
                                new KeyFrame (
                                        Duration.seconds (getRandomBetween (3, 4)),
                                        new KeyValue (rotate.angleProperty (), 360, Interpolator.LINEAR)
                                )
                        );
                        target.getTimelines ().add (timeline);
                        timeline.setCycleCount (Timeline.INDEFINITE);
                        timeline.play ();

                        (( Group ) this.getRoot ()).getChildren ().addAll (target);

                        break;
                    }
                }
            }
            if(Score.getScoreRef().getTargetsHit() == TARGETS){
                goToNextLevel();
            }
        });

    }

    @Override
    public Group buildSceneGroup ( ) {
        Group root = new Group ();

        if ( Score.getScoreRef ().getTargetsLeft () > 0 ) {
            for ( int i = 0 ; i < 4 ; i++ ) {
                Target target = new TargetSecondLevel ();
                Translate initialTranslate = new Translate (offset[i], 0);
                Rotate rotate = new Rotate ();

                Translate finalTranslate = new Translate (WIDTH / 2., HEIGHT / 2.);

                target.getTransforms ().addAll (
                        finalTranslate,
                        rotate,
                        new Scale (0.55, 0.55),
                        initialTranslate
                );


                Timeline timeline = new Timeline (
                        new KeyFrame (
                                Duration.ZERO,
                                new KeyValue (rotate.angleProperty (), 0, Interpolator.LINEAR)
                        ),
                        new KeyFrame (
                                Duration.seconds (getRandomBetween (2, 4)),
                                new KeyValue (rotate.angleProperty (), 360, Interpolator.LINEAR)
                        )
                );
                target.getTimelines ().add (timeline);
                timeline.setCycleCount (Timeline.INDEFINITE);
                timeline.play ();

                root.getChildren ().addAll (target);
            }

        }

        return root;
    }

    @Override
    public void goToNextLevel ( ) {
        ThirdLevel thirdLevel = new ThirdLevel (new Group(), WIDTH, HEIGHT);
        Score.getScoreRef().setTargetsHit(0);
        Main.stage.setScene (thirdLevel);
    }

    @Override
    public void repeatLevel ( ) {
        Score.getScoreRef ().setScore (lastScore);
        Score.getScoreRef().setTargetsHit(0);
        for ( boolean b : track ) {
            b = false;
        }
        SecondLevel secondLevel = new SecondLevel(new Group(), WIDTH, HEIGHT);
        Main.stage.setScene (secondLevel);
    }

    @Override
    public Rectangle setBackground ( ) {

        return null;
    }

    @Override
    public Text getLevelSignature ( ) {
        return new Text("Level Two");
    }
}
