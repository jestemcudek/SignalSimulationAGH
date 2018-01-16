package sample;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private double x;
    private double y;
    private int fuelTank;  //liczba kroków, które może przebyć robot
    private List<Signal> antenaList;
    private List<Signal> prevAntList; //możliwe do usunięcia, do zastąpienia przez tmp przekazywana do funkcji isWorth
    private int count=0;

    public Robot(double x, double y, int tank){
        this.x = x;
        this.y = y;
        this.fuelTank=tank;
        this.antenaList = new ArrayList<Signal>();
        this.prevAntList = new ArrayList<Signal>();
    }

    public double getX() {
        return x;
    }

    public int getFuelTank() {
        return fuelTank;
    }

    public double getY() {
        return y;
    }

    public List<Signal> getAntenaList() {
        return antenaList;
    }

    public void listenToAntenas(List<Antena> antlist){
        for (Antena ant:antlist) {
            antenaList.add(Detector.measureSignal(this.x,this.y,ant));
        }
    }

    private List<Signal> listenToAntenas(double tmp_x, double tmp_y,List<Antena> antenaList){
        List<Signal> result = new ArrayList<>();
        for (Antena ant:antenaList) {
            result.add(Detector.measureSignal(tmp_x,tmp_y,ant));
        }
        return result;
    }

    private int findTheWeakestSignal(List<Signal> antlist){
        int result=0;
        Signal tmp = antlist.get(0);
        if(antlist.size()>1) {
            for (int i = 1; i < antlist.size(); i++) {
                if (antlist.get(i).getReceivedPowerSignal() < tmp.getReceivedPowerSignal()) {
                    tmp = antlist.get(i);
                    result = i;
                }
            }
            System.out.println(result);
            return result;
        }else
            return 0;
    }

    public void tryMakeToBetterPosition(List<Antena> antList){
        if(antenaList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Nie nastąpiło wstępne nasłuchiwanie anten");
            alert.showAndWait();
            return;
        }
        if(antenaList.size()>0){
            int idx = findTheWeakestSignal(antenaList);
            switch (lookForNextMove(idx,antList)){
                case West: makeAMove(Directions.West); break;
                case North: makeAMove(Directions.North); break;
                case East: makeAMove(Directions.East); break;
                case South: makeAMove(Directions.South); break;
            }
        }
        else makeAMove(Directions.randomDirection()); //robot pójdzie w losowym kierunku "pójdzie przed siebie"


    }

    private Directions lookForNextMove(int lowestSignal,List<Antena> antenaList){
        if(isWorthToChangePosition(lowestSignal,listenToAntenas(this.x,this.y-1,antenaList)))
            return Directions.North;
        else if (isWorthToChangePosition(lowestSignal,listenToAntenas(this.x+1,this.y,antenaList)))
            return Directions.East;
        else if(isWorthToChangePosition(lowestSignal,listenToAntenas(this.x,this.y+1,antenaList)))
            return Directions.South;
        else return Directions.West;

    }

    private void makeAMove(Directions direction){
        switch (direction){
            case East: this.x++; break;
            case North: this.y--; break;
            case West: this.x--; break;
            case South: this.y++; break;
            default: System.out.println("Nie można wykonać kroku");
        }
        this.fuelTank--;
    }

    private boolean isWorthToChangePosition(int idx,List<Signal> possibleSignalList){
       if(possibleSignalList.get(idx).getReceivedPowerSignal()>antenaList.get(idx).getReceivedPowerSignal())
        return true;
        else
            return false;
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
