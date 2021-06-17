package sample.Targets;

import sample.Levels.ThirdLevel;

public class TargetThirdLevel extends Target{
    @Override
    public void MarkTrack ( double x, double y ) {
        ThirdLevel.setHit(true);
        //System.out.println("aaaaaaaaaaaaaaaaa");
    }
}
