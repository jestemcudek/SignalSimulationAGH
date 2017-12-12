package sample;

public class Antena {
    private int x;
    private int y;
    private String ID;
    private int signalPower;

    public Antena(int x,int y,int signalPower){
        this.x=x;
        this.y=y;
        this.signalPower=signalPower;
        this.ID="ANT"+x+y+signalPower;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSignalPower() {
        return signalPower;
    }
}
