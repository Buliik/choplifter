package sample.sprites;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.AnimatedImage;

import java.util.Random;

public class Dude extends Sprite
{
    private AnimatedImage self;
    private Boolean inChopper = false;
    private double wayTime = 0;
    private char way = 'l';

    public Dude(double x) {
        self = new AnimatedImage();
        this.setFitHeight(30);
        this.setFitWidth(20);
        this.setLayoutX(x);
        this.setLayoutY(400);
        Image[] imageArray = new Image[8];
        for(int i = 0; i < 8; i++)
            imageArray[i] = new Image( "src/imgs/guy" + i + ".png");
        self.frames = imageArray;
        self.duration = 0.1;
        velocityX = 1;
        velocityY = 0;
    }

    public boolean doStuff(double t, double vx, double vy) {
        if(inChopper) return false;

        if(this.getLayoutX() < 200)
        {
            this.moveLeft();
            this.setScaleX(-1);
        }

        else if(vy == 385 && vx - this.getLayoutX() < 100 && vx - this.getLayoutX() > 0)
        {
            this.moveRight();
            this.setScaleX(1);
        }

        else if(vy == 385 && this.getLayoutX() - vx < 100 && this.getLayoutX() - vx > 0)
        {
            this.moveLeft();
            this.setScaleX(-1);
        }

        else
        {
            if(t - wayTime > 2)
            {
                wayTime = t;
                if (new Random().nextInt(10) % 2 == 0) { way = 'r'; }
                else { way = 'l'; }
            }

            if(way == 'l')
            {
                this.moveLeft();
                this.setScaleX(-1);
            }
            else
            {
                this.moveRight();
                this.setScaleX(1);
            }
        }

        setImage(self.getFrame(t));
        if(this.getLayoutX() < 5) return true;

        return false;

    }

    public void getInDaChopper(Chopper chopper) {
        if(this.getLayoutX() > 200)
            if(chopper.load(this))
            {
                inChopper = true;
                ((Pane) this.getParent()).getChildren().remove(this);
            }
    }

    public void getOut(double x) {
        this.setLayoutX(x);
        inChopper = false;
    }
}
