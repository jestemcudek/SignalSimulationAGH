package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private int amountOfAntenas =3;
    private List<Antena> antenaList = new ArrayList<>();

    @FXML
    private Canvas simCanvas;

    @FXML
    private Button simButton;



    private GraphicsContext gc;
    private boolean hasStarted = false;


    public void initizializeSimulation() {
        //simButton.setDisable(true);
        Random random = new Random();
        int fuel = random.nextInt(300)+1;
        double robot_x = random.nextDouble()*simCanvas.getWidth();
        double robot_y = random.nextDouble()*simCanvas.getHeight();
        Robot robot = new Robot(robot_x,robot_y,fuel);
        Image robotimage = new Image("robot_symbol.png");
        Image antenaimage = new Image("signal_symbol.png");
        gc = simCanvas.getGraphicsContext2D();
        drawElement(robotimage, robot_x, robot_y, gc);
        List<Point> pointList = generatePoints(random);
        int reach = findLongestDistance(pointList);
        for(int i=0;i<amountOfAntenas;i++){
            Point tmp = pointList.get(i);
            Antena ant = new Antena(tmp.x,tmp.y,reach, 20);
            drawElement(antenaimage,tmp.x,tmp.y,gc);
        }


    }

    private void drawElement(Image img, double x, double y, GraphicsContext gc){
        gc.drawImage(img,x,y,32.0,32.0);
    }

    private List<Point> generatePoints(Random random){
        List<Point> result = new ArrayList<>();
        for(int i=0;i<3;i++){
            Point tmp = new Point();
            tmp.x = random.nextDouble()*simCanvas.getWidth();
            tmp.y = random.nextDouble()*simCanvas.getHeight();
            result.add(tmp);
        }
        return result;
    }

    private int findLongestDistance(List<Point> pointList){
     int result=0;
     double tmp=0.0;
     for(int i=0;i<amountOfAntenas;i++){
         for(int j=i+1;j<amountOfAntenas;j++){
             tmp = measureDistance(pointList.get(i),pointList.get(j));
         }
     }
     result = (int)tmp;
     return result;
    }

    private double measureDistance(Point a, Point b){
        double diff_x = b.x-a.x;
        double diff_y = b.y-a.y;
        return Math.sqrt(Math.pow(diff_x,2.0)+Math.pow(diff_y,2.0));
    }
}
