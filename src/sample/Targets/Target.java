package sample.Targets;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import sample.Levels.FirstLevel;
import sample.util.Score;

import java.util.ArrayList;

public class Target extends Group {

    private static final int MIN_CIRCLES = 3;
    private static final int MAX_CIRCLES = 7;
    private static final double CIRCLE_WIDTH = 15;
    private double diameter;

    private ArrayList <Timeline> timelines = new ArrayList <Timeline> ();

    public ArrayList <Timeline> getTimelines () {
        return timelines;
    }


    public double getRandomBetween (double min, double max) {
        return Math.random () * (max - min) + min;

    }

    public double getDiameter () {
        return diameter;
    }

    public Target () {
        int numOfCircles = ( int ) getRandomBetween (MIN_CIRCLES, MAX_CIRCLES);
        double diameter = numOfCircles * CIRCLE_WIDTH;

        for ( int i = 0 ; i < numOfCircles ; i++ ) {

            Color[] outerColors = {
                    Color.BLACK,
                    Color.WHITE
            };
            Circle circle = new Circle (diameter - CIRCLE_WIDTH * i, outerColors[i % 2]);
            this.getChildren ().addAll (circle);
            if ( i == numOfCircles - 1 ) {
                Color[] centerColors = {
                        Color.RED,
                        Color.ORANGE,
                        Color.YELLOW,
                        Color.GREENYELLOW,
                        Color.BLUEVIOLET
                };
                circle.setFill (centerColors[( int ) getRandomBetween (0, centerColors.length)]);
            }
        }
        for ( int i = 0 ; i < numOfCircles ; i++ ) {
            Text text = new Text (String.valueOf ((numOfCircles - i) * 10));
            text.getTransforms ().addAll (
                    new Translate (i * CIRCLE_WIDTH + 1, 3),
                    new Scale (0.8, 0.8)
            );
            text.setFill (Color.RED);
            //center
            if ( i == 0 ) {
                text.getTransforms ().addAll (
                        new Translate (- 8, 0)
                );
                text.setFill (Color.BLACK);
            }
            this.getChildren ().addAll (text);
        }

        Score.getScoreRef ().setTargetsLeft (Score.getScoreRef ().getTargetsLeft () - 1);
        Score.getScoreRef ().targetsLeftText.setText (Integer.toString (Score.getScoreRef ().getTargetsLeft ()));

        this.addEventFilter (MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(Score.getScoreRef ().getBulletsLeft ()>0){
                //calculating score
                double distance = Math.sqrt (mouseEvent.getX () * mouseEvent.getX () + mouseEvent.getY () * mouseEvent.getY ());
                int points = ( int ) (diameter - distance) / ( int ) CIRCLE_WIDTH * 10 + 10;
                Score.getScoreRef ().addToScore (( int ) (diameter - distance) / ( int ) CIRCLE_WIDTH * 10 + 10);
                Score.getScoreRef ().scoreText.setText (Integer.toString (Score.getScoreRef ().getScore ()));

                Score.getScoreRef().setTargetsHit(Score.getScoreRef().getTargetsHit()+1);

                //removing target
                this.setVisible (false);

                Text text = new Text (Integer.toString (points));
                text.getTransforms ().addAll (
                        new Scale (2, 2)
                );

                FadeTransition fadeTransition = new FadeTransition (Duration.millis (1000), text);
                fadeTransition.setFromValue (1);
                fadeTransition.setToValue (0);
                fadeTransition.setAutoReverse (false);
                fadeTransition.play ();

                this.getChildren ().clear ();
                this.getChildren ().addAll (text);
                for ( Timeline t : getTimelines () ) {
                    t.stop ();
                }
                this.setVisible (true);

                double x = mouseEvent.getSceneX ();
                double y = mouseEvent.getSceneY ();

                //Marking tracks i levels to generate new targets
                MarkTrack (x, y);
            }
            else{//no bullets left

            }

        });
    }

    public void MarkTrack (double x, double y) { }
}