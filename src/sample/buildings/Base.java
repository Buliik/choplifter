package sample.buildings;

import javafx.scene.image.Image;

public class Base extends Building
{
    public Base()
    {
        dudesLeft = 0;
        this.setLayoutX(10);
        this.setLayoutY(300);
        this.setFitHeight(200);
        this.setFitWidth(200);
        this.setImage(new Image("src/imgs/base.png"));
    }
}
