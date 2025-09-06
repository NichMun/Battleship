import java.util.*;
public class gridGuess { // Used for storing all relavent information for guess
    private int lasthit;
    private int guess; 
    private int lastguess;
    private boolean hit;
    private boolean newsunkenship;
    private ArrayList <Integer> potentialGuesses;
    private int secondlasthit;
    private int direction;
    private boolean[] bools;
    private boolean hitActual;

    public gridGuess() { // Sets up default values
        secondlasthit = -1;
        lasthit = -1;
        guess = -1;
        lastguess = -1;
        hit = false;
        hitActual = false;
        newsunkenship = false;
        direction = -1;
        bools = new boolean[]{true, true, true, true};
    }

    public boolean getHitActual() {
        return hitActual;
    }

    public void setHitActual(boolean hitActual) {
        this.hitActual = hitActual;
    }




    public boolean[] getBools() {
        return bools;
    }

    public void setBool (int i, boolean bool) {
        bools[i] = bool; 
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLastHit() {
        return lasthit;
    }

    public int getSecondLastHit() {
        return secondlasthit;
    }

    public int getGuess() {
        return guess;
    }

    public int getLastGuess() {
        return lastguess;
    }

    public boolean getHit() {
        return hit;
    }

    public boolean getNewSunkenShip() {
        return newsunkenship;
    }

    public void setLastHit(int lasthit) {
        this.lasthit = lasthit;
    }

    public void setSecondLastHit(int secondlasthit) {
        this.secondlasthit = secondlasthit;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public void setLastGuess(int lastguess) {
        this.lastguess = lastguess;
    }

    public void setHit(boolean hit) { 
        this.hit = hit;
        newsunkenship = false;
    }





    public void setNewSunkenShip() { // Resets the values to their default if a new ship has sunken
        newsunkenship = true;
        lastguess = -1;
        guess = -1;
        lasthit = -1;
        hit = false;
        bools[0] = true;
        bools[1] = true;
        bools[2] = true;
        bools[3] = true;
        direction = -1;
    }

 


}
