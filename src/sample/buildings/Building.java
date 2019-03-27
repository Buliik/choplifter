package sample.buildings;

import javafx.scene.image.ImageView;

public class Building extends ImageView
{
    protected int dudesLeft;
    private double lastPrisonerReleased;

    public boolean freePrisoner(double t) {
        if(t - lastPrisonerReleased > 0.5 && dudesLeft > 0)
        {
            lastPrisonerReleased = t;
            dudesLeft--;
            return true;
        }
        return false;
    }
}
