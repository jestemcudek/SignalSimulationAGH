package sample;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private int x;
    private int y;
    private int fuelTank;  //liczba kroków, które może przebyć robot
    private List<Signal> antenaList;
    private List<Signal> prevAntList; //możliwe do usunięcia, do zastąpienia przez tmp przekazywana do funkcji isWorth
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
            count++;
        }
    }

    private int findTheWeakest(List<Signal> antlist){
        int result=0;
        Signal tmp = antlist.get(0);
        for(int i=1;i<antlist.size();i++){
            if(antlist.get(i).getReceivedPowerSignal()<tmp.getReceivedPowerSignal()) {
                tmp = antlist.get(i);
                result=i;
            }
        }
        return result;
    }


    public ChangeParams isWorthToChangePosition(){
        //Przegladaj listy prev i antlist
        //Napisz funkcje ktora porownuje moce sygnalu
        //Jezeli najslabszy sygnal wzrosl, a pozostale nie obnizyly sie ponizej poziomu krytycznego zwroc true, w kazdym innym przypadku false
        ChangeParams params = new ChangeParams();
        int changecount=0;
        for(int i=0;i<3;i++){
            if(prevAntList.get(i).getReceivedPowerSignal()<antenaList.get(i).getReceivedPowerSignal())
                changecount++;
            else if(prevAntList.get(i).getReceivedPowerSignal()>antenaList.get(i).getReceivedPowerSignal())
                changecount--;
            else
                System.out.println("Bez zmian");
        }
        if(changecount>0) { //jeżeli odnotowano, że potencjalna zmiana położenia będzie lepsza, to zwracamy true, w pp zwracamy false
            params.isItWorth= true;
        }
        else {
            params.isItWorth= false;
        }
        params.gain=changecount;
        return params;
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
