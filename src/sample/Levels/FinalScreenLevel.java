package sample.Levels;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import sample.util.Main;
import sample.util.Score;



public class FinalScreenLevel extends BaseLevel {

    public FinalScreenLevel ( Parent parent, double width, double height, int numberOfTargets, int numberOfBullets ) {
        super(parent, width, height, numberOfTargets, numberOfBullets,4);
        buildLevel(buildSceneGroup());
    }


    @Override
    public Group buildSceneGroup ( ) {
        Group root = new Group();
        Label text = new Label("Your score is :   " + Score.getScoreRef().scoreText.getText());
        text.getTransforms().addAll(
                new Translate(WIDTH/2-200, HEIGHT/2-100),
                new Scale(3,3)
        );

        Label playAgain = new Label("Play again");
        playAgain.getTransforms().addAll(
                new Translate(WIDTH/2-100, HEIGHT/2+60),
                new Scale(3,3)
        );
        playAgain.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent->{
            Score.getScoreRef().resetScore();
            goToNextLevel();
        });


        root.getChildren().addAll(text, playAgain);
        return root;
    }

    @Override
    public void goToNextLevel ( ) {
        Score.getScoreRef().setTargetsHit(0);
        FirstLevel firstLevel = new FirstLevel (new Group(), WIDTH, HEIGHT);
        Main.stage.setScene (firstLevel);
    }

    @Override
    public void repeatLevel ( ) {

    }

    @Override
    public Rectangle setBackground ( ) {

        return null;
    }

    @Override
    public Text getLevelSignature ( ) {
            return new Text("Final score");
    }
}
