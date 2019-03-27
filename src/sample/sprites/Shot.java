package sample.sprites;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shot extends Sprite
{
    private int direction;
    private Circle circle;

    public Shot(double x, double y, int dir)
    {
        velocityX = 4;
        velocityY = 4;
        circle = new Circle(x, y, 5);
        circle.setFill(Color.ORANGE);
        direction = dir;
    }

    public void doStuff(boolean friend)
    {
        if(friend) moveDown();
        else moveUp();

        switch(direction)
        {
            case 1: moveLeft();
            break;
            case 2: moveRight();
            break;
            default: break;
        }
    }

    public void moveRight()
    {
        if(this.circle.getCenterX() < ((Pane)circle.getParent()).getPrefWidth())
            this.circle.setCenterX(this.circle.getCenterX() + velocityX);
    }

    public void moveLeft()
    {
        if(this.circle.getCenterX() > 0)
            this.circle.setCenterX(this.circle.getCenterX() - velocityX);
    }

    public void moveDown()
    {
        if(this.circle.getCenterY() < ((Pane)circle.getParent()).getPrefHeight())
            this.circle.setCenterY(this.circle.getCenterY() + velocityY);
    }

    public void moveUp()
    {
        if(this.circle.getCenterY() > this.circle.getCenterY() - velocityY)
            this.circle.setCenterY(this.circle.getCenterY() - velocityY);
    }

    public void destroy() { ((AnchorPane) this.circle.getParent()).getChildren().remove(this.circle); }

    public Circle getCircle() { return circle; }

}
