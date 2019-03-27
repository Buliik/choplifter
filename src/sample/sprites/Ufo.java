package sample.sprites;
import javafx.scene.image.Image;
import sample.AnimatedImage;

public class Ufo extends Sprite
{
    private AnimatedImage self;
    private double place;


    public Ufo(double x) {
        self = new AnimatedImage();
        Image[] imageArray = new Image[6];
        this.setFitHeight(30);
        this.setFitWidth(30);
        place = x;
        for(int i = 0; i < 6; i++)
            imageArray[i] = new Image( "src/imgs/e_f" + i + ".png");
        self.frames = imageArray;
        self.duration = 0.100;
    }


    public void doStuff(double t) {
        double x = place + 128 * Math.cos(t);
        double y = 232 + 128 * Math.sin(t);
        this.setPosition(x, y);
        setImage(self.getFrame(t));
    }
}
