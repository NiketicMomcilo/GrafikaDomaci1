package sample.Targets;

import sample.Levels.FirstLevel;
import sample.Levels.SecondLevel;

public class TargetSecondLevel extends Target {
    public TargetSecondLevel () {
        super ();
    }

    public void MarkTrack (double x, double y) {
        double distance = Math.sqrt (Math.abs (x - 500) * Math.abs (x - 500) + Math.abs (y - 450) * Math.abs (y - 450)) * 20 / 11;
        if ( distance < 210 ) {
            SecondLevel.track[0] = true;
        } else if ( distance < 420 ) {
            SecondLevel.track[1] = true;
        } else if ( distance < 630 ) {
            SecondLevel.track[2] = true;
        } else {
            SecondLevel.track[3] = true;
        }

    }


}
