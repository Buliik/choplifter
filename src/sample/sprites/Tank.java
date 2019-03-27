package sample.sprites;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Tank extends Sprite
{
    private double ls = 0;
    private int direction = 0;

    public Tank(double x){
        this.setLayoutX(x);
        this.setLayoutY(450);
        this.setFitHeight(30);
        this.setFitWidth(50);
        this.setImage(new Image("src/imgs/tank.png"));
        velocityX = 1;
        velocityY = 0;
    }


    public Shot doStuff(double x, double t, Pane pane){

        if(this.getLayoutX() < 200)
            this.setLayoutX(200);

        if(x - this.getLayoutX() > 0)
        {
            this.setScaleX(-1);
            this.moveRight();
            direction = 2;
        }

        else if (x - this.getLayoutX() < 0)
        {
            this.setScaleX(1);
            this.moveLeft();
            direction = 1;
        }

        else direction = 0;

        Shot last = null;

        if(t - ls > 1.0)
        {
            ls = t;
            last = this.shoot();
            pane.getChildren().add(last.getCircle());
        }

        return last;
    }


    private Shot shoot() { return new Shot(this.getLayoutX(), this.getLayoutY(), direction); }
}
