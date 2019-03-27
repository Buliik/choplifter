package sample.sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Sprite extends ImageView implements IColide
{
    protected double velocityX;
    protected double velocityY;

    public Sprite(){
        this.setLayoutX(256);
        this.setLayoutY(256);
        velocityX = 0;
        velocityY = 0;
    }


    public void setPosition(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }


    public void moveRight() {
        if(this.getLayoutX() < ((Pane)this.getParent()).getPrefWidth() - this.getFitWidth())
            this.setLayoutX(this.getLayoutX() + velocityX);
    }


    public void moveLeft() {
        if(this.getLayoutX() > 0)
            this.setLayoutX(this.getLayoutX() - velocityX);
    }


    public void moveDown() {
        if(this.getLayoutY() < 385)
            this.setLayoutY(this.getLayoutY() + velocityY);
    }


    public void moveUp() {
        if(this.getLayoutY() > 0)
            this.setLayoutY(this.getLayoutY() - velocityY);
    }


    @Override
    public Rectangle2D getBoundary() { return new Rectangle2D(this.getLayoutX(), this.getLayoutY(), this.getFitWidth(), this.getFitHeight()); }


    @Override
    public boolean intersects(IColide s) { return s.getBoundary().intersects( this.getBoundary()); }


    public void destroy() { ((AnchorPane) this.getParent()).getChildren().remove(this); }
}
