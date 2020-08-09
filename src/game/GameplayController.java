package game;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.function.Function;

import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.RED;

import java.util.Timer;
import java.util.TimerTask;

public class GameplayController {
    /**
     * Simply instantiation of a player object
     */
    double fuelConsumptionRate = 1.0;
    int tickRateMS = 10;
    PlayerStats player = new PlayerStats(
            0, 0, 100, 0,
            0, 0, 0.1, 500
    );


    Timer currentGameTickTimer;
    Timer carAnimationTimer;

    @FXML private Label dateValueLabel;
    @FXML private Label hungerValueLabel;
    @FXML private Label thirstValueLabel;
    @FXML private Label fuelValueLabel;
    @FXML private Label restroomValueLabel;
    @FXML private Label fatigueValueLabel;
    @FXML private Label nextLandmarkValueLabel;
    @FXML private Label lastLandmarkValueLabel;
    @FXML private Label distanceTraveledValueLabel;
    @FXML private Label speedValueLabel;
    @FXML private Label cashValueLabel;
    @FXML private Button speedUpButton;
    @FXML private Button slowDownButton;
    @FXML public ImageView moose;
    @FXML public ImageView carImageView;
    @FXML public GridPane insertPane;
    @FXML public Button avoidButton;
    public boolean mooseActive;


    /**
     * Fired when the .fxml and controller has loaded.
     * Begins the gameplay ticking.
     */
    public void initialize() {
        cashValueLabel.setText(String.valueOf(player.getCash()));
        beginTick();
        mooseActive = false;
        System.out.println("Initial lastLandmarkIndex = "+player.landmarkAttributes[player.getLastLandmarkIndex()+1][1]);
    }


    /**
     * Creates a Timer and Task that will execute every tickRateMS;
     * within updatePlayerStats, it will:
     * - calculate new player stat values
     * - update the corresponding fxml Labels
     */
    public void beginTick() {
        updatePlayerStatsLabels(player);
        currentGameTickTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    updatePlayerStats(player);

                    if(player.getSpeed() > 0) {
                        rumbleCar();
                    }

                    if (mooseActive) {
                        tickMoose();
                    } else if (reachedLandmark()) {
                        cancelTick();
                        loadNextLandmarkScene();
                    } else {
                        if (player.getSpeed() > 0 && mooseSpawnRoll() == true) {
                            activateMooseEvent();
                        }
                    }
                });
            }
        };
        currentGameTickTimer.scheduleAtFixedRate(task, 0, tickRateMS);
    }
//    public void beginTick() {
//        updatePlayerStatsLabels(player);
//        currentGameTickTimer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    updatePlayerStats(player);
//
//                    if(player.getSpeed() > 0) {
//                        rumbleCar();
//                    }
//
//                    if (reachedLandmark()) {
//                        cancelTick();
//                        loadNextLandmarkScene();
//                    } else if (mooseActive == true) {
//                        tickMoose();
//                    };
//                });
//            }
//        };
//        currentGameTickTimer.scheduleAtFixedRate(task, 0, tickRateMS);
////        updatePlayerStatsLabels(player);
////        currentGameTickTimer = new Timer();
////        TimerTask task = new TimerTask() {
////            @Override
////            public void run() {
////                Platform.runLater(() -> {
////                    updatePlayerStats(player);
////                    if (reachedLandmark()) {
////                        cancelTick();
////                        loadNextLandmarkScene();
////                    } else if (mooseActive == true) {
////                        tickMoose();
////                    };
////                });
////            }
////        };
////        currentGameTickTimer.scheduleAtFixedRate(task, 0, tickRateMS);
//
//    }

    public Boolean mooseSpawnRoll() {
        Random random = new Random();
        Float roll = random.nextFloat();

        if (roll <= 0.001f && mooseActive == false) {
            return true;
        } else {
            return false;
        }
    }

    public boolean reachedLandmark() {
        //if (player.getDistanceTraveled() >= player.landmarkAttributes[player.getLastLandmarkIndex()+1][1]) {
        System.out.println("reached landmark");
        System.out.println(player.landmarkAttributes[player.getLastLandmarkIndex()+1][1]);
        if (player.getDistanceTraveled() >=
                Integer.parseInt(player.landmarkAttributes[player.getLastLandmarkIndex()+1][1])) {
            return true;
        }
        return false;
    }

    public void loadNextLandmarkScene() {
        switch(player.getLastLandmarkIndex()+1) {
            case 1:
                // load St. John's landmark scene
                break;
            case 2:
                // load Paradise landmark scene
                break;
            case 3:
                // load CBS landmark scene
                break;
            case 4:
                // load Holyrood landmark scene
                break;
            case 5:
                // load Brigus Junction landmark scene
                break;
            case 6:
                // load Bellevue landmark scene
                break;
            case 7:
                // load Goobies landmark scene
                break;
            case 8:
                // load Clarenville landmark scene
                break;
            case 9:
                // load Glovertown landmark scene
                break;
            case 10:
                // load Gambo landmark scene
                break;
            case 11:
                // load Gander landmark scene
                break;
            case 12:
                // load Glenwood landmark scene
                break;
            case 13:
                // load Bishop's Falls landmark scene
                break;
            case 14:
                // load Grand Falls - Windsor landmark scene
                break;
            case 15:
                // load Badger landmark scene
                break;
            case 16:
                // load South Brook landmark scene
                break;
            case 17:
                // load Sheppardville landmark scene
                break;
            case 18:
                // load Deer Lake landmark scene
                break;
            case 19:
                // load Pasadena landmark scene
                break;
            case 20:
                // load Corner Brook OR load the Game Win function
                break;
        }
        for (int i=0; i < player.landmarkAttributes.length; i++) {
            if (player.getLastLandmarkIndex()+1 == 1) {
                // load St. John's scene
            }
        }
    }

    /**
     * Sets the player object's new stat values,
     * ensures the values are within the range 0 to 100, and
     * updates the corresponding Labels in the UI.
     *
     * @param player
     */
    public void updatePlayerStats(PlayerStats player) {
        if (player.getFuel() <= 110) { // Player burns 20% more fuel traveling over 100kmh
            player.setFuel(player.getFuel() - player.getFuelRate() * player.getSpeed());
        } else {
            player.setFuel(player.getFuel() - player.getFuelRate() * player.getSpeed() * 1.1);
        }
        player.setHunger(player.getHunger() + player.getHungerRate());
        player.setThirst(player.getThirst() + player.getThirstRate());
        player.setRestroom(player.getRestroom() + player.getRestroomRate());
        player.setFatigue(player.getFatigue() + player.getFatigueRate());
        player.setDistanceTraveled(player.getDistanceTraveled() + player.getSpeed()/100000); // numeric value controls the ratio between distance traveled and speed

        player.clampPlayerStats();
        updatePlayerStatsLabels(player);
    }

    /**
     * A method that updates player status labels to current running values, truncated (via casting) to an integer
     *
     * @param player as an instance of PlayerStats class and gets its properties
     */
    //TODO: Should updates stats of the player during a time period (per second?!)
    public void updatePlayerStatsLabels(PlayerStats player) {
        hungerValueLabel.setText(String.valueOf((int) player.getHunger()));
        thirstValueLabel.setText(String.valueOf((int) player.getThirst()));
        fuelValueLabel.setText(String.valueOf((int) player.getFuel()));
        restroomValueLabel.setText(String.valueOf((int) player.getRestroom()));
        fatigueValueLabel.setText(String.valueOf((int) player.getFatigue()));
        speedValueLabel.setText(String.valueOf(player.getSpeed()));
        //TODO: Date stats label should be updated later on the screen

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        //String strDate = dateFormat.format(player.getCurrentDate());
        dateValueLabel.setText(player.getCurrentDate().toString()); // replace this with a function to update only when X time has elapsed
        nextLandmarkValueLabel.setText(player.getNextLandmarkName());
        lastLandmarkValueLabel.setText(player.getLastLandmarkName());
        //distanceTraveledValueLabel.setText(String.valueOf(player.getDistanceTraveled()) + " km");
//        distanceTraveledValueLabel.setText(player.getDistanceTraveled() + " km");
        distanceTraveledValueLabel.setText(Math.round(player.getDistanceTraveled()*100.0)/100.0 + " km"); // rounds the distance traveled to 2 decimal places for the Label
    }

    /**
     * Stops the game tick (i.e. player stat updates and moose spawning)
     */
    public void cancelTick() {
        // This will be called when a landmark is reached and when game over happens.
        currentGameTickTimer.cancel();
        currentGameTickTimer.purge();
    }

    public GameplayController() throws ParseException, IOException, ClassNotFoundException {}

    public void main(String[] args) throws InterruptedException {}

    public void startDriving() {

    }

    public void enterLandmark() {
        cancelTick();
        // load new scene, making sure it has a reference to the current player object
    }

    public void gameOver(String reason) {
        cancelTick();
        System.out.println(reason);
    }

    public void gameVictory() {
        cancelTick();
        // Display
    }

    public void animateCarStartStop(String startStop) {
        // TODO: use the code below to make the car slightly move up and down every second;
        //  so animateCar should create a Timer that fires the code below regularly.
        //  Then, we can add this into gameplay loop wherever the gameplay begins or resumes but only when speed > 0
        //  implement a reference to the car sprite in the way that the moose sprite is handled
//
//
//        if (startStop.equals("start")) {
//            carAnimationTimer = new Timer();
//            double carYoffset = 0;
//            TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
//                Platform.runLater(() -> {
//                    animateCar(carYoffset);
//                    carYoffset = carYoffset + 1; // i left off on this hacky animation of the car here once i realized you cannot manipulate variables this way
//
//                });
//                }
//            };
//            currentGameTickTimer.scheduleAtFixedRate(task, 0, 100);
//
//
//
//            if (plusMinusInversion) {
//                plusMinusInversion = false;
////               double carYPosition = car.getTranslateY();
////               car.setTranslateX(carYPosition + 4); // put on a timer that alternates with the next line
//            } else {
//                plusMinusInversion = true;
////                double carYPosition = car.getTranslateY();
////                car.setTranslateX(carYPosition - 4); // put on a timer that alternates with the next line
//            }
//
////        double carYPosition = car.getTranslateY();
////        car.setTranslateX(carYPosition + 4); // put on a timer that alternates with the next line
////        car.setTranslateX(carYPosition - 4); // alternates
//        } else if (startStop.equals("stop")){
//
//        }


//        System.out.println("animateCarStartStop() fired");//DEBUG
    }
//
//    public void animateCar (double carYoffset) {
//        boolean plusMinusInversion = false;
//        if (startStop.equals("start")) {
//            if (plusMinusInversion) {
//                plusMinusInversion = false;
////               double carYPosition = car.getTranslateY();
////               car.setTranslateX(carYPosition + 4); // put on a timer that alternates with the next line
//            } else {
//                plusMinusInversion = true;
////                double carYPosition = car.getTranslateY();
////                car.setTranslateX(carYPosition - 4); // put on a timer that alternates with the next line
//            }
//
////        double carYPosition = car.getTranslateY();
////        car.setTranslateX(carYPosition + 4); // put on a timer that alternates with the next line
////        car.setTranslateX(carYPosition - 4); // alternates
//        } else if (startStop.equals("stop")){
//
//        }
//        //        System.out.println("animateCar() fired");//DEBUG
//    }



    /**
     * checks if car has collided with an 850lb ham on stilts
     * @param moose moose object
     * @param car   car object
     * @return true if horrific auto accident, false otherwise
     */
    @FXML
    public Boolean checkIfMooseCollision(ImageView moose, ImageView car) {
        // calculate movement/collision bounds of the moose
        double mooseXPosMax = moose.getBoundsInParent().getMaxX();
        double carXPosMin = car.getBoundsInParent().getMinX();
        return (mooseXPosMax >= carXPosMin);
    }

    @FXML
    public void avoidButtonClicked(ActionEvent event) {
        mooseActive = false;
        resetMooseEvent();
    }

    /**
     * fired once for each tick while the moose is active
     */
    @FXML
    public void tickMoose() {
        if (mooseActive == true) {
            if (checkIfMooseCollision(moose, carImageView)) {
                gameOver("You hit a big ole moose"); //replace later
                resetMooseEvent();
                mooseActive = false;
            } else {
                animateMooseAtSpeed();
            }
        } else {
            System.out.println("tickMoose() fired while mooseActive==false"); //DEBUG
        }
    }

    /**
     * begins the moose event, sets it to an active state
     */
    @FXML
    public void activateMooseEvent() {
        System.out.println("MOOSE EVENT ACTIVATED"); //debug
        mooseActive = true;

        //place moose
        moose.setTranslateX(0);
        moose.setVisible(true);

        //place button
        double newButtonPositionX = 300 + (Math.random() * 100);
        avoidButton.setVisible(true);
        avoidButton.setTranslateX(newButtonPositionX);

    }
    /**
     * resets the moose event to its original state
     */
    @FXML
    public void resetMooseEvent() {
        System.out.println("MOOSE EVENT RESET"); //debug
        mooseActive = false;

        moose.setTranslateX(0);
        moose.setVisible(false);

        avoidButton.setTranslateX(100);
        avoidButton.setVisible(false);
    }



    /**
     * animates moose relative to player's current speed
     */
    @FXML
    public void animateMooseAtSpeed() {
        double mooseXPosition = moose.getTranslateX();
        moose.setTranslateX(mooseXPosition + (player.getSpeed() / 25));
        System.out.println("animateMooseAtSpeed() fired");//DEBUG
    }

    @FXML
    void slowDownBtnClicked(ActionEvent event) {
        double newSpeed = player.getSpeed() - 5;
        //System.out.println(newSpeed);
        player.setSpeed(newSpeed);
        speedValueLabel.setText(String.valueOf((player.getSpeed())));

    }

    @FXML
    void speedUpBtnClicked(ActionEvent event) {
        double newSpeed = player.getSpeed() + 5;
        //System.out.println(newSpeed);
        player.setSpeed(newSpeed);
        speedValueLabel.setText(String.valueOf((player.getSpeed())));
        if (newSpeed > 75) {
            //TODO: Fuel should get consumed in faster rate
        }
    }

    @FXML
    void speed50BtnClicked(ActionEvent event) {
        //System.out.println(50);
        player.setSpeed(50);
        speedValueLabel.setText(String.valueOf((player.getSpeed())));
    }

    @FXML
    void speed100BtnClicked(ActionEvent event) {
        //System.out.println(100);
        player.setSpeed(100);
        speedValueLabel.setText(String.valueOf((player.getSpeed())));
    }

    @FXML
    public void rumbleCar() {
        if (carImageView.getRotate() != 1) {
            carImageView.setRotate(1);
        } else {
            carImageView.setRotate(-1);
        }
    }
}
