package sample;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private int x;
    private int y;
    private int fuelTank;  //liczba kroków, które może przebyć robot
    private List<Signal> antenaList;
    private List<Signal> prevAntList;
    private int count=0;

    public Robot(int x, int y, int tank){
        this.x = x;
        this.y = y;
        this.fuelTank=tank;
        this.antenaList = new ArrayList<Signal>();
        this.prevAntList = new ArrayList<Signal>();
    }

    public int getX() {
        return x;
    }

    public int getFuelTank() {
        return fuelTank;
    }

    public int getY() {
        return y;
    }

    public List<Signal> getAntenaList() {
        return antenaList;
    }

    public void listenToAntenas(List<Antena> antlist){
        if(count!=0) {
            prevAntList = antenaList;
            antenaList.clear();
        }
        for (Antena ant:antlist) {
            antenaList.add(Detector.measureSignal(this.x,this.y,ant));
        }
    }

    public boolean isWorthToChangePosition(){
        //Przegladaj listy prev i antlist
        //Napisz funkcje ktora porownuje moce sygnalu
        //Jezeli najslabszy sygnal wzrosl, a pozostale nie obnizyly sie ponizej poziomu krytycznego zwroc true, w kazdym innym przypadku false
        return true;
    }

    public boolean isYourLocationSafe(){
        if(antenaList.size()!=3)
            return false;
         //jezeli wszystkie sygnaly z trzech anten beda wystraczajaco mocne, to zwracamy true, w przeciwnym razie false
        for(Signal sig:antenaList){
            if(!sig.isSignalStrongEnough()) {
                return false;
            }
        }
        return true;
    }
}
