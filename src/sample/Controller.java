package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.buildings.Base;
import sample.buildings.Building;
import sample.buildings.Prison;
import sample.sprites.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable
{
    private double st, tt = 0, ut = 0;
    private Chopper chopper;
    private ArrayList<Shot> shotsFired = new ArrayList<>();
    private ArrayList<Sprite> enemies = new ArrayList<>();
    private ArrayList<Shot> enemyShots = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Dude> myDudes = new ArrayList<>();
    private final long startNanoTime = System.nanoTime();
    private int kills = 0, savedDudes = 0;
    private double LabelOS = 180;
    private ArrayList<String> input = new ArrayList<String>();
    public static Stage primStage;

    @FXML
    private ImageView bgImg;

    @FXML
    private AnchorPane shotFired;

    @FXML
    private Label Label1;

    public void handlePressed(KeyEvent event)
    {
        String code = event.getCode().toString();

        if(!input.contains(code))
            input.add(code);
    }

    public void handleReleased(KeyEvent event)
    {
        String code = event.getCode().toString();
        input.remove(code);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        primStage.addEventHandler(KeyEvent.KEY_PRESSED, new javafx.event.EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                handlePressed(event);
            }
        });
        primStage.addEventHandler(KeyEvent.KEY_RELEASED, new javafx.event.EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                handleReleased(event);
            }
        });

        chopper = new Chopper();

        buildings.add(new Base());


        for(int i = 0; i < 3; i++)
        {
            buildings.add(new Prison(200+800*(i+1)));
        }


        this.shotFired.getChildren().addAll(buildings);
        this.shotFired.getChildren().addAll(chopper);

        Label1.setLayoutX(400 - LabelOS);


        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double rand = new Random().nextInt((int)bgImg.getFitWidth());

                if((input.contains("LEFT") || input.contains("A")) && chopper.getLayoutY() < 385)
                    chopper.moveLeft();
                if((input.contains("RIGHT") || input.contains("D")) && chopper.getLayoutY() < 385)
                    chopper.moveRight();
                if(input.contains("UP") || input.contains("W"))
                    chopper.moveUp();
                if(input.contains("DOWN") || input.contains("S"))
                    chopper.moveDown();
                if(!input.contains("LEFT") && !input.contains("A") && !input.contains("RIGHT") && !input.contains("D"))
                    chopper.direction = 0;
                if(input.contains("SPACE") && (t - st >= 0.2))
                {
                    shotsFired.add(chopper.shoot());
                    st = t;
                    shotFired.getChildren().add(shotsFired.get(shotsFired.size() - 1).getCircle());
                }


                if(chopper.getLayoutX() < 200 && chopper.getLayoutY() == 385)
                {
                    chopper.loadOut(t);
                }


                if(t - tt >= 8)
                {
                    tt = t;

                    while((rand - chopper.getLayoutX() < 300 && rand - chopper.getLayoutX() > 0) ||
                            (chopper.getLayoutX() - rand < 300 && chopper.getLayoutX() - rand > 0))
                    {
                        rand = new Random().nextInt((int) bgImg.getFitWidth());
                    }

                    enemies.add(new Tank(rand));
                    shotFired.getChildren().add(enemies.get(enemies.size() - 1));
                }


                if(t - ut >= 13)
                {
                    ut = t;

                    while((rand - chopper.getLayoutX() < 100 && rand - chopper.getLayoutX() > 0) ||
                            (chopper.getLayoutX() - rand < 100 && chopper.getLayoutX() - rand > 0))
                    {
                        rand = new Random().nextInt((int) bgImg.getFitWidth());
                    }

                    enemies.add(new Ufo(rand));
                    shotFired.getChildren().add(enemies.get(enemies.size() - 1));
                }


                if(chopper.getLayoutX() > 400 && chopper.getLayoutX() + chopper.getFitWidth() < bgImg.getFitWidth() - 400)
                    Label1.setLayoutX(chopper.getLayoutX() - LabelOS);

                Label1.setText("Enemies Killed: " + String.valueOf(kills) + "\tDudes saved: " + String.valueOf(savedDudes) +
                        "\tIn helicopter: " + String.valueOf(chopper.getNumberOfPassengers()) + "/16");


                for (int i = 0; i < shotsFired.size(); i++)
                {
                    shotsFired.get(i).doStuff(true);

                    if (shotsFired.get(i).getCircle().getBoundsInParent().getMaxX() >= bgImg.getFitWidth() ||
                            shotsFired.get(i).getCircle().getBoundsInParent().getMinX() <= 0 ||
                            shotsFired.get(i).getCircle().getBoundsInParent().getMaxY() >= bgImg.getFitHeight())
                    {
                        shotsFired.get(i).destroy();
                        shotsFired.remove(shotsFired.get(i));
                        return;
                    }


                    for(int j = 0; j < enemies.size(); j++)
                    {
                        if(shotsFired.get(i).getCircle().intersects(enemies.get(j).getBoundsInParent()))
                        {
                            shotsFired.get(i).destroy();
                            shotsFired.remove(shotsFired.get(i));
                            enemies.get(j).destroy();
                            enemies.remove(enemies.get(j));
                            kills++;
                            break;
                        }
                    }
                }


                for(int i = 0; i < buildings.size(); i++)
                {
                    if(buildings.get(i).getLayoutX() - chopper.getLayoutX() < 100 && chopper.getLayoutX() - buildings.get(i).getLayoutX() < 300)
                        if(buildings.get(i).freePrisoner(t))
                        {
                            myDudes.add(new Dude(buildings.get(i).getLayoutX() + new Random().nextInt((int)buildings.get(i).getFitWidth())));
                            shotFired.getChildren().addAll(myDudes.get(myDudes.size() - 1));
                        }
                }


                for(int i = 0; i < myDudes.size(); i++)
                {
                    if(myDudes.get(i).doStuff(t, chopper.getLayoutX(), chopper.getLayoutY()))
                    {
                        savedDudes++;
                        myDudes.get(i).destroy();
                        myDudes.remove(myDudes.get(i));
                        i--;
                    }

                    else if (shotFired.getChildren().contains(myDudes.get(i)) && chopper.intersects(myDudes.get(i)) && chopper.getLayoutY() == 385)
                    {
                        myDudes.get(i).getInDaChopper(chopper);
                    }
                }


                for(int i = 0; i < enemyShots.size(); i++)
                {
                    if (enemyShots.get(i).getCircle().intersects(chopper.getBoundsInParent()))
                    {
                        double xpos;
                        if(chopper.getLayoutX() < 190) xpos = 190;
                        else if (chopper.getLayoutX() > bgImg.getFitWidth() - 190) xpos = bgImg.getFitWidth() - 190;
                        else xpos = chopper.getLayoutX();
                        Text defeat = new Text(xpos, 100, "You just lost the game.");
                        defeat.setFont(new Font(40));
                        defeat.setFill(Color.RED);
                        defeat.setTextAlignment(TextAlignment.CENTER);
                        shotFired.getChildren().addAll(defeat);
                        stop();
                    }

                    enemyShots.get(i).doStuff(false);
                }


                for(int i = 0; i < enemies.size(); i++)
                {
                    if(chopper.intersects(enemies.get(i)))
                    {
                        double xpos;
                        if(chopper.getLayoutX() < 190) xpos = 190;
                        else if (chopper.getLayoutX() > bgImg.getFitWidth() - 190) xpos = bgImg.getFitWidth() - 190;
                        else xpos = chopper.getLayoutX();
                        Text defeat = new Text(xpos, 100, "You just lost the game.");
                        defeat.setFont(new Font(40));
                        defeat.setFill(Color.RED);
                        defeat.setTextAlignment(TextAlignment.CENTER);
                        shotFired.getChildren().addAll(defeat);
                        stop();
                    }
                    else if(enemies.get(i) instanceof Ufo)
                        ((Ufo)enemies.get(i)).doStuff(t);
                    else if (enemies.get(i) instanceof Tank)
                    {
                        enemyShots.add(((Tank)enemies.get(i)).doStuff(chopper.getLayoutX(), t, shotFired));
                        if(enemyShots.get(enemyShots.size() - 1) == null)
                            enemyShots.remove(enemyShots.size() - 1);

                    }
                }


                if(savedDudes == 48)
                {
                    Text victory = new Text(chopper.getLayoutX() - 80, 100, "Glorious victory, you managed to save all guys!");
                    victory.setFont(new Font(36));
                    victory.setFill(Color.GREEN);
                    victory.setTextAlignment(TextAlignment.CENTER);
                    shotFired.getChildren().addAll(victory);
                    stop();
                }

            }


        }.start();
    }
}
