package sample;

public class Antena {
    private double x;
    private double y;
    private String ID;
    private int signalRange;
    private double minWorkingSignal;

    public Antena(double x,double y,int signalRange, double minWorkingSignal){
        this.x=x;
        this.y=y;
        this.signalRange=signalRange;
        this.minWorkingSignal = minWorkingSignal;
        this.ID="ANT"+(int)x+(int)y;
    }

    double getX() {
        return x;
    }

    double getY() {
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
