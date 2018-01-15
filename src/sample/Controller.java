package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Controller {

    @FXML
    private Canvas simCanvas;

    private GraphicsContext gc;


    public void initizializeSimulation() {
        Random random = new Random();
        int fuel = random.nextInt(300)+1;
        double robot_x = random.nextDouble()*1000;
        double robot_y = random.nextDouble()*600;
        Robot robot = new Robot(robot_x,robot_y,fuel);
        Image image = new Image("signal_symbol.png");
        //gc = canvas.getGraphicsContext2D();
        if(simCanvas!=null) {
            gc = simCanvas.getGraphicsContext2D();
            drawElement(image, robot_x, robot_y, gc);
        }else{
            System.out.println("Nie ma canvasa");
        }

    }

    public void drawElement(Image img, double x, double y, GraphicsContext gc){
        gc.setFill(Color.BLACK);
        //gc.drawImage(img,10,10);
        gc.drawImage(img,x,y,32.0,32.0);
    }


}
