package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private int amountOfAntenas =3;
    private List<Antena> antenaList = new ArrayList<>();
    private Robot robot;

    @FXML
    private Canvas simCanvas;

    @FXML
    private Button simButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label simLabel;

    private GraphicsContext gc;
    private boolean hasStarted = false;

    private Image robotimage = new Image("robot_symbol.png");
    private Image antenaimage = new Image("signal_symbol.png");


    public void initizializeSimulation() {
        simButton.setDisable(true);
        Random random = new Random();
        int fuel = random.nextInt(300)+50;
        double robot_x = random.nextDouble()*simCanvas.getWidth()-50;
        double robot_y = random.nextDouble()*simCanvas.getHeight();
        robot = new Robot(robot_x,robot_y,fuel);
        simLabel.setText("Paliwo: "+robot.getFuelTank());
        gc = simCanvas.getGraphicsContext2D();
        drawElement(robotimage, robot_x, robot_y, gc);
        List<Point> pointList = generatePoints(random);
        int reach = findLongestDistance(pointList);
        for(int i=0;i<amountOfAntenas;i++){
            Point tmp = pointList.get(i);
            Antena ant = new Antena(tmp.x,tmp.y,reach, 0);
            antenaList.add(ant);
            drawElement(antenaimage,tmp.x,tmp.y,gc);
            drawCircle(tmp.x, tmp.y,reach, gc);
        }
        robot.listenToAntenas(antenaList);
        System.out.println(robot.getAntenaList().size());
        System.out.println(robot.getFuelTank());
        simulate();
        simButton.setDisable(false);
        robot=null;
        antenaList.clear();
        gc.clearRect(0,0,simCanvas.getWidth(),simCanvas.getHeight());
    }

    public void simulate(){
        boolean foundSafePlace = robot.isYourLocationSafe();
        while(!foundSafePlace&&robot.getFuelTank()>0){
            double x = robot.getX();
            double y = robot.getY();
            robot.tryMakeToBetterPosition(antenaList);
            gc.clearRect(x,y,32,32);
            simLabel.setText("Paliwo: "+robot.getFuelTank());
            drawElement(robotimage,robot.getX(),robot.getY(),gc);
            robot.listenToAntenas(antenaList);
            foundSafePlace=robot.isYourLocationSafe();

        }
        if(robot.isYourLocationSafe()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText(null);
            alert.setContentText("Robot znalazł się w bezpiecznej lokalizacji");
            alert.showAndWait();
        }else if(robot.getFuelTank()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Porażka");
            alert.setHeaderText(null);
            alert.setContentText("Robotowi nie udało się dotrzeć do robociego nieba");
            alert.showAndWait();
        }
    }

    //TODO wyświetlanie paliwa - ilość pozostałych kroków

    private void drawElement(Image img, double x, double y, GraphicsContext gc){
        gc.drawImage(img,x,y,32.0,32.0);
    }

    private void drawCircle(double x, double y, double signalRange, GraphicsContext gc ){
        double circleWidth = 2*signalRange;
        double currentX = x - circleWidth/2;
        double currentY = y - circleWidth/2;
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
        gc.strokeOval(currentX, currentY, circleWidth, circleWidth);
    }

    private List<Point> generatePoints(Random random){
        double rangeMinX = 200;
        double rangeMaxX = 800;
        double rangeMinY = 200;
        double rangeMaxY = 500;
        List<Point> result = new ArrayList<>();
        for(int i=0;i<3;i++){
            Point tmp = new Point();
            //tmp.x = random.nextDouble()*simCanvas.getWidth();
            //tmp.y = random.nextDouble()*simCanvas.getHeight();
            tmp.x = rangeMinX + (rangeMaxX - rangeMinX) * random.nextDouble();
            tmp.y = rangeMinY + (rangeMaxY - rangeMinY) * random.nextDouble();
            result.add(tmp);
        }
        return result;
    }

    private int findLongestDistance(List<Point> pointList){
     int result=0;
     double tmp=0;
     double tmp2=0;
     for(int i=0;i<amountOfAntenas;i++){
         for(int j=i+1;j<amountOfAntenas;j++){
             tmp2 = measureDistance(pointList.get(i),pointList.get(j));
             if(tmp<tmp2)tmp=tmp2;
             System.out.println(tmp);
         }
     }
     result = (int)tmp;
        System.out.println(result);

     return result;
    }

    private double measureDistance(Point a, Point b){
        double diff_x = b.x-a.x;
        double diff_y = b.y-a.y;
        return Math.sqrt(Math.pow(diff_x,2.0)+Math.pow(diff_y,2.0));
    }
}
