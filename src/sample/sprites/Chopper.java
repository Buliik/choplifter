package sample.sprites;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class Chopper extends Sprite
{
    public int direction;
    private final int capacity = 16;
    private int dudes = 0;
    private double mid_window;
    private double lastOut = 0;
    private ArrayList<Dude> passengers = new ArrayList<>();

    public Chopper() {
        super();
        this.setPosition(100,385);
        velocityX = 3;
        velocityY = 3;
        this.setFitHeight(50);
        this.setFitWidth(50);
        this.setImage(new Image("src/imgs/chopper1.png"));
        mid_window = 375;
    }

    public int getNumberOfPassengers() { return this.passengers.size(); }

    public void loadOut(double t) {

        if(passengers.size() >= 1 && t - lastOut > 0.5)
        {
            lastOut = t;
            passengers.get(0).getOut(this.getLayoutX());
            ((Pane)this.getParent()).getChildren().add(passengers.get(0));
            passengers.remove(0);
            dudes--;
        }

    }


    public boolean load(Dude passenger){
        if (dudes < capacity)
        {
            dudes++;
            passengers.add(passenger);
            return true;
        }
        else return false;
    }


    public void moveRight() {
        super.moveRight();
        this.setScaleX(-1);
        Pane posuv = ((Pane)this.getParent());
        if( posuv.getPrefWidth() - this.getBoundsInParent().getMaxX() > mid_window && this.getBoundsInParent().getMinX() > mid_window)
        {
            posuv.setLayoutX(-this.getBoundsInParent().getMinX() + mid_window);

        }
        direction = 2;
    }


    public void moveLeft() {
        super.moveLeft();
        this.setScaleX(1);
        Pane posuv = ((Pane)this.getParent());
        if( this.getBoundsInParent().getMinX() > mid_window && posuv.getPrefWidth() - this.getBoundsInParent().getMaxX() > mid_window )
        {
            posuv.setLayoutX(-this.getBoundsInParent().getMinX() + mid_window);
        }
        direction = 1;
    }


    public Shot shoot() { return new Shot(this.getLayoutX() + this.getFitWidth()/2 , this.getLayoutY() + this.getFitHeight()/2, direction); }
}
