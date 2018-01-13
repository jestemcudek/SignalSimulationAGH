package sample;

public class Signal {
    private String AntID; //identyfikator anteny
    private double receivedPowerSignal; //moc sygnalu
    private boolean isSignalStrongEnough; //flaga czy sygnal jest wystarczajaco mocny

    public void setAntID(String antID) {
        AntID = antID;
    }

    public String getAntID() {
        return AntID;
    }

    public void setReceivedPowerSignal(double receivedPowerSignal) {
        this.receivedPowerSignal = receivedPowerSignal;
    }

    public double getReceivedPowerSignal() {
        return receivedPowerSignal;
    }

    public void setSignalStrongEnough(boolean signalStrongEnough) {
        isSignalStrongEnough = signalStrongEnough;
    }

    public boolean isSignalStrongEnough() {
        return isSignalStrongEnough;
    }
}
