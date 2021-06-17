package sample.util;

import javafx.scene.text.Text;

public class Score {
    private static Score s = null;

    private int score = 0;

    private int targetsLeft;
    private int maxTargets;
    private int targetsHit;

    private int bulletsLeft;
    private int maxBullets;

    public int getMaxTargets () {
        return maxTargets;
    }

    public int getTargetsHit ( ) {
        return targetsHit;
    }

    public static void resetScore(){
        getScoreRef().setScore(0);
    }

    public void setTargetsHit ( int targetsHit ) {
        this.targetsHit = targetsHit;
    }

    public void setMaxTargets ( int maxTargets) {
        this.maxTargets = maxTargets;
    }

    public int getMaxBullets () {
        return maxBullets;
    }

    public void setMaxBullets (int maxBullets) {
        this.maxBullets = maxBullets;
    }

    public int getBulletsLeft(){
        return bulletsLeft;
    }
    public void setBulletsLeft(int n){
        this.bulletsLeft = n;
    }

    public int getScore () {
        return score;
    }

    public void setScore (int s) {
        score = s;
    }

    public void addToScore (int n) {
        score += n;
    }

    public int getTargetsLeft () {
        return targetsLeft;
    }

    public void setTargetsLeft (int targetsLeft) {
        this.targetsLeft = targetsLeft;
    }

    public Text scoreText ;
    public Text targetsLeftText ;
    public Text bulletsLeftText;

    public static Score getScoreRef () {
        if ( s == null ) {
            s = new Score ();
        }
        return s;
    }
}