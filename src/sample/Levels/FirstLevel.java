package sample.Levels;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import sample.Targets.TargetFirstLevel;
import sample.util.Main;
import sample.util.Score;
import sample.Targets.Target;

public class FirstLevel extends BaseLevel {

    public static boolean track[] = { false, false, false, false };
    private static final int offset[] = { MAX_CIRCLE_WIDTH, 3 * MAX_CIRCLE_WIDTH, 5 * MAX_CIRCLE_WIDTH, 7 * MAX_CIRCLE_WIDTH };
    private static final int TARGETS = 20;
    private static final int BULLETS = 25;
    private int lastScore;


    public FirstLevel (Parent parent, double width, double height) {
        super (parent, width, height, TARGETS, BULLETS);
        for ( boolean b : track ) {
            b = false;
        }
        lastScore = Score.getScoreRef ().getScore ();
        buildLevel (buildSceneGroup ());

        this.addEventHandler (MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if ( Score.getScoreRef ().getTargetsLeft () > 0 ) {
                for ( int i = 0 ; i < 4 ; i++ ) {
                    if ( track[i] == true ) {
                        track[i] = false;
                        Target target = new TargetFirstLevel ();
                        Translate t = new Translate ();
                        target.getTransforms ().addAll (t);
                        Timeline timeline = new Timeline (
                                new KeyFrame (
                                        Duration.ZERO,
                                        new KeyValue (t.xProperty (), 105, Interpolator.LINEAR),
                                        new KeyValue (t.yProperty (), offset[i], Interpolator.LINEAR)
                                ),
                                new KeyFrame (
                                        Duration.seconds (getRandomBetween (0.75, 2.75)),
                                        new KeyValue (t.xProperty (), WIDTH - 70, Interpolator.LINEAR),
                                        new KeyValue (t.yProperty (), offset[i], Interpolator.LINEAR)
                                )
                        );
                        target.getTimelines ().add (timeline);
                        timeline.setAutoReverse (true);
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
    public Group buildSceneGroup () {
        Group root = new Group ();
        for ( int i = 0 ; i < 4 ; i++ ) {
            Target target = new TargetFirstLevel ();
            Translate t = new Translate ();
            target.getTransforms ().addAll (t);
            Timeline timeline = new Timeline (
                    new KeyFrame (
                            Duration.ZERO,
                            new KeyValue (t.xProperty (), 105, Interpolator.LINEAR),
                            new KeyValue (t.yProperty (), offset[i], Interpolator.LINEAR)
                    ),
                    new KeyFrame (
                            Duration.seconds (getRandomBetween (0.75, 3)),
                            new KeyValue (t.xProperty (), WIDTH - 70, Interpolator.LINEAR),
                            new KeyValue (t.yProperty (), offset[i], Interpolator.LINEAR)
                    )
            );
            target.getTimelines ().add (timeline);
            timeline.setAutoReverse (true);
            timeline.setCycleCount (Timeline.INDEFINITE);
            timeline.play ();
            root.getChildren ().addAll (target);
        }

        return root;
    }

    @Override
    void goToNextLevel () {
        SecondLevel secondLevel = new SecondLevel (new Group(), WIDTH, HEIGHT);
        Score.getScoreRef().setTargetsHit(0);
        Main.stage.setScene (secondLevel);

    }

    @Override
    void repeatLevel () {
        Score.getScoreRef ().setScore (lastScore);
        Score.getScoreRef().setTargetsHit(0);
        for ( boolean b : track ) {
            b = false;
        }
        FirstLevel firstLevel = new FirstLevel (new Group(), WIDTH, HEIGHT);
        Main.stage.setScene (firstLevel);

    }


}
