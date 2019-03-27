package sample.buildings;

import javafx.scene.image.Image;

public class Prison extends Building
{
    public Prison(double x)
    {
        dudesLeft = 16;
        this.setLayoutX(x);
        this.setLayoutY(250);
        this.setFitHeight(200);
        this.setFitWidth(200);
        this.setImage(new Image("src/imgs/building.png"));
    }

}
