package sample.sprites;

import javafx.geometry.Rectangle2D;

public interface IColide
{
    Rectangle2D getBoundary();
    boolean intersects(IColide s);
}
