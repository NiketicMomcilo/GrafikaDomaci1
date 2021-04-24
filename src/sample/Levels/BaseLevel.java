package sample.Levels;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import sample.util.Score;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseLevel extends Scene {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 900;
    public static final int KEYBOARD_MOVEMENT_DELTA = 5;
    public static int MAX_CIRCLE_WIDTH = 105;

    public abstract Group buildSceneGroup ( );

    public abstract void goToNextLevel ( );

    public abstract void repeatLevel ( );

    public abstract Text getLevelSignature();


    public BaseLevel ( Parent parent, double width, double height, int numberOfTargets, int numberOfBullets ) {
        super(parent, width, height);
        this.setRoot(showScoreData(width, height, numberOfTargets, numberOfBullets));
        this.setCursor(getCustomImageCursor());

        Robot robot = new Robot();
        AtomicInteger horizontal = new AtomicInteger();
        var ref = new Object() {
            AtomicInteger vertical = new AtomicInteger();
        };


        this.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            Score.getScoreRef().setBulletsLeft(Score.getScoreRef().getBulletsLeft() - 1);
            if ( Score.getScoreRef().getBulletsLeft() >= 0 ) {
                Score.getScoreRef().bulletsLeftText.setText(Integer.toString(Score.getScoreRef().getBulletsLeft()));
            }
            if ( Score.getScoreRef().getBulletsLeft() == 0 ) {
                repeatLevel();
            }
        });


        this.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                    switch ( keyEvent.getCode() ) {
                        case UP -> {
                            ref.vertical.set(- KEYBOARD_MOVEMENT_DELTA);
                            robot.mouseMove(robot.getMouseX() + horizontal.get(), robot.getMouseY() + ref.vertical.get());

                        }
                        case DOWN -> {
                            ref.vertical.set(KEYBOARD_MOVEMENT_DELTA);
                            robot.mouseMove(robot.getMouseX() + horizontal.get(), robot.getMouseY() + ref.vertical.get());
                        }
                        case LEFT -> {
                            horizontal.set(- KEYBOARD_MOVEMENT_DELTA);
                            robot.mouseMove(robot.getMouseX() + horizontal.get(), robot.getMouseY() + ref.vertical.get());
                        }
                        case RIGHT -> {
                            horizontal.set(KEYBOARD_MOVEMENT_DELTA);
                            robot.mouseMove(robot.getMouseX() + horizontal.get(), robot.getMouseY() + ref.vertical.get());
                        }

                    }
                }
        );


        this.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch ( keyEvent.getCode() ) {
                case UP, DOWN -> ref.vertical.set(0);
                case LEFT, RIGHT -> horizontal.set(0);
            }
        });


        this.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {

            if ( keyEvent.getCode().equals(KeyCode.SPACE) ) {
                Robot robot1 = new Robot();
                robot1.mouseClick(MouseButton.PRIMARY);
            }
        });

    }

    private ImageCursor getCustomImageCursor ( ) {

        Image image = new Image("/sample/TransparentAim.png", 60, 60, true, true);
        ImageCursor imageCursor = new ImageCursor(image, 30, 30);

        return imageCursor;
    }

    protected Group showScoreData ( double width, double height, int numberOfTargets, int numberOfBullets ) {
        Group group = new Group();

        Score.getScoreRef().scoreText = new Text(Integer.toString(Score.getScoreRef().getScore()));
        Score.getScoreRef().targetsLeftText = new Text(Integer.toString(Score.getScoreRef().getTargetsLeft()));
        Score.getScoreRef().bulletsLeftText = new Text(Integer.toString(Score.getScoreRef().getTargetsLeft()));

        Score.getScoreRef().setMaxBullets(numberOfBullets);
        Score.getScoreRef().setBulletsLeft(numberOfBullets);
        Score.getScoreRef().bulletsLeftText.setText(Integer.toString(Score.getScoreRef().getBulletsLeft()));

        Score.getScoreRef().setMaxTargets(numberOfTargets);
        Score.getScoreRef().setTargetsLeft(numberOfTargets);
        Score.getScoreRef().targetsLeftText.setText(Integer.toString(Score.getScoreRef().getTargetsLeft()));


        Text bulletsLeftLabel = new Text("Bullets left: ");
        bulletsLeftLabel.getTransforms().addAll(
                new Translate(0, 0.91 * height),
                new Scale(2, 2)
        );

        Score.getScoreRef().bulletsLeftText.getTransforms().addAll(
                new Translate(170, 0.91 * height),
                new Scale(2, 2)
        );

        Text maxBulletsLabel = new Text("/" + Score.getScoreRef().getMaxBullets());
        maxBulletsLabel.getTransforms().addAll(
                new Translate(200, 0.91 * height),
                new Scale(2, 2)
        );


        Text targetsLeftLabel = new Text("Targets left: ");
        targetsLeftLabel.getTransforms().addAll(
                new Translate(0, 0.95 * height),
                new Scale(2, 2)
        );
        Score.getScoreRef().targetsLeftText.getTransforms().addAll(
                new Translate(170, 0.95 * height),
                new Scale(2, 2)
        );

        Text maxTargetsLabel = new Text("/" + Score.getScoreRef().getMaxTargets());
        maxTargetsLabel.getTransforms().addAll(
                new Translate(200, 0.95 * height),
                new Scale(2, 2)
        );


        Text scoreLabel = new Text("Score: ");
        scoreLabel.getTransforms().addAll(
                new Translate(0, 0.99 * height),
                new Scale(2, 2)
        );

        Score.getScoreRef().scoreText.getTransforms().addAll(
                new Translate(100, 0.99 * height),
                new Scale(2, 2)
        );

        Text levelSignature = getLevelSignature();

        levelSignature.getTransforms().addAll(
                new Translate(WIDTH-levelSignature.getText().length()*14, HEIGHT -10),
                new Scale( 1.8,1.8)
        );


        group.getChildren().addAll(
                scoreLabel,
                Score.getScoreRef().scoreText,

                targetsLeftLabel,
                Score.getScoreRef().targetsLeftText,
                maxTargetsLabel,

                bulletsLeftLabel,
                maxBulletsLabel,
                Score.getScoreRef().bulletsLeftText,

                levelSignature
        );

        return group;
    }

    public void buildLevel ( Group group ) {
        ( ( Group ) this.getRoot() ).getChildren().addAll(group);
    }

    public double getRandomBetween ( double min, double max ) {
        return Math.random() * ( max - min ) + min;
    }


}
