package sample;

public class Antena {
    private int x;
    private int y;
    private String ID;
    private int signalRange;
    private double minWorkingSignal;

    public Antena(int x,int y,int signalRange, double minWorkingSignal){
        this.x=x;
        this.y=y;
        this.signalRange=signalRange;
        this.minWorkingSignal = minWorkingSignal;
        this.ID="ANT"+x+y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getSignalRange() {
        return signalRange;
    }

    double getMinWorkingSignal() {
        return minWorkingSignal;
    }

    String getID() {
        return ID;
    }
}
