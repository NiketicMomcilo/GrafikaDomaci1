package sample.Targets;

import javafx.scene.input.MouseEvent;
import sample.Levels.FirstLevel;

public class TargetFirstLevel extends Target{
    public TargetFirstLevel () {
        super();
    }

    @Override
    public void MarkTrack (double x,double y) {
        if ( y < 210 ) {
            FirstLevel.track[0] = true;
        } else if ( y < 420 ) {
            FirstLevel.track[1] = true;
        } else if ( y < 630 ) {
            FirstLevel.track[2] = true;
        } else {
            FirstLevel.track[3] = true;
        }
    }
}
