package game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.io.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameplayController {
    /**
     * Simply instantiation of a player object
     */
    double fuelConsumptionRate = 1.0;
    int tickRateMS = 10;

    PlayerStats player = new PlayerStats(
            50, 51, 52, 53,0,
            10.5, 55, 1
    );
    // player is always initialized to default values, will be loaded in separate function
//    PlayerStats player = new PlayerStats(
//        0, 0, 100, 0,0,
//        1.95, 500
//    );
//    debug test - just before victory condition (game win at 692 traveled)
//    PlayerStats player = new PlayerStats(
//        0, 0, 100, 0,0,
//        0, 691, 500, 19
//    );

    // default player stats
//    PlayerStats player = new PlayerStats(
//            0, 0, 100, 0,0,
//            0, 0.1, 500
//    );
    public boolean mooseActive;
    Timer currentGameTickTimer;
    Timer carAnimationTimer;
    String [][] savingObj = {
            {"hunger", String.valueOf(player.getHunger())},
            {"thirst", String.valueOf(player.getThirst())},
            {"fuel", String.valueOf(player.getFuel())},
//            {"restroom", String.valueOf(player.getRestroom())}, // restroom is removed
            {"fatigue", String.valueOf(player.getFatigue())},
            {"speed", String.valueOf(player.getSpeed())},
            {"distanceTraveled", String.valueOf(player.getDistanceTraveled())},
            {"cash", String.valueOf(player.getCash())},
            {"lastLandmarkIndex", String.valueOf(player.getLastLandmarkIndex())}
    };


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
    @FXML private Button saveGameButton;
    @FXML public ImageView moose;
    @FXML public ImageView carImageView;
    @FXML public GridPane insertPane;
    @FXML public Button avoidButton;
    @FXML public Label gameOverLabel;
    @FXML public Button backToMainMenuButton;
    @FXML public ImageView explosion;




    /**
     * Fired when the .fxml and controller has loaded.
     * Begins the gameplay ticking.
     */
    public void initialize() {
        cashValueLabel.setText(String.valueOf(player.getCash()));
        beginTick();
        mooseActive = false;
        //System.out.println("Initial lastLandmarkIndex = "+player.getLastLandmarkIndex());
    }

    public PlayerStats getPlayer() {
        return player;
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
                        player.setDistanceTraveled(Double.parseDouble(player.landmarkAttributes[player.getLastLandmarkIndex()+1][1]));
                        if (player.getLastLandmarkIndex()+1 == 20) {
                            gameVictory();
                        } else {

                            try {
                                loadNextLandmarkScene();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
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
//                    if (mooseActive) {
//                        tickMoose();
//                    } else if (reachedLandmark()) {
//                        cancelTick();
//                        if (player.getLastLandmarkIndex()+1 == 21) {
//                            gameVictory();
//                        } else {
//
//                            try {
//                                loadNextLandmarkScene();
//                            } catch (IOException ioException) {
//                                ioException.printStackTrace();
//                            }
//                        }
//                    } else {
//                        if (player.getSpeed() > 0 && mooseSpawnRoll() == true) {
//                            activateMooseEvent();
//                        }
//                    }
//                });
//            }
//        };
//        currentGameTickTimer.scheduleAtFixedRate(task, 0, tickRateMS);
//    }

    public boolean reachedLandmark() {
        if (player.getDistanceTraveled() >=
                Integer.parseInt(player.landmarkAttributes[player.getLastLandmarkIndex()+1][1])) {
            //System.out.println("reachedLandmark(); Last landmark index = "+player.getLastLandmarkIndex());
            return true;
        }
        return false;
    }

    public void loadNextLandmarkScene() throws IOException {
        switch(player.getLastLandmarkIndex()+1) { // switch on the index of the reachedLandmark
            case 1:
                // load St. John's landmark scene
                Stage currentStage = (Stage) carImageView.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Landmark.fxml"));
                Parent root = (Parent) loader.load();
                LandmarkController landmarkController = loader.getController();
                landmarkController.storePlayer(player);
                landmarkController.storeSavingObj(savingObj);
                landmarkController.updateLandmarkStatsLabels(player);
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("You are at a Landmark. Make the right choice!");
                stage.setScene(scene);

                System.out.println(landmarkController);

                stage.show();
                currentStage.close();
                break;
            case 2:
                // load Paradise landmark scene
//                Stage currentStage = (Stage) carImageView.getScene().getWindow();
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("Landmark.fxml"));
//                Parent root = (Parent) loader.load();
//                LandmarkController landmarkController = loader.getController();
//                landmarkController.storePlayer(player);
//                landmarkController.storeSavingObj(savingObj);
//                landmarkController.updateLandmarkStatsLabels(player);
//                Stage stage = new Stage();
//                Scene scene = new Scene(root);
//
//                stage.setTitle("You are at a Landmark. Make the right choice!");
//                stage.setScene(scene);
//
//                System.out.println(landmarkController);
//
//                stage.show();
//                currentStage.close();
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
//        for (int i=0; i < player.landmarkAttributes.length; i++) {
//            if (player.getLastLandmarkIndex()+1 == 1) {
//                // load St. John's scene
//            }
//        }
    }

    /**
     * has a change to spawn a moose
     * @return if a moose was spawned by this
     */
    public Boolean mooseSpawnRoll() {
        Random random = new Random();
        Float roll = random.nextFloat();

        if (roll <= 0.001f && mooseActive == false) {
            return true;
        } else {
            return false;
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
        if (player.getSpeed() <= 110) { // Player burns 20% more fuel traveling over 110kmh
            player.setFuel(player.getFuel() - player.getFuelRate() * player.getSpeed());
        } else {
            player.setFuel(player.getFuel() - player.getFuelRate() * player.getSpeed() * 1.1);
        }
        savingObj[2][1] = String.valueOf(player.getFuel());

        player.setHunger(player.getHunger() + player.getHungerRate());
        savingObj[0][1] = String.valueOf(player.getHunger());

        player.setThirst(player.getThirst() + player.getThirstRate());
        savingObj[1][1] = String.valueOf(player.getThirst());

        player.setFatigue(player.getFatigue() + player.getFatigueRate());
        savingObj[3][1] = String.valueOf(player.getFatigue());

        savingObj[4][1] = String.valueOf(player.getSpeed());

        player.setDistanceTraveled(player.getDistanceTraveled() + player.getSpeed()/100000); // numeric value controls the ratio between distance traveled and speed
        savingObj[5][1] = String.valueOf(player.getDistanceTraveled());

        savingObj[6][1] = String.valueOf(player.getCash());

        savingObj[7][1] = String.valueOf(player.getLastLandmarkIndex());

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
        restroomValueLabel.setText("0");
        fatigueValueLabel.setText(String.valueOf((int) player.getFatigue()));
        speedValueLabel.setText(String.valueOf(player.getSpeed()));
        cashValueLabel.setText(String.valueOf((int) player.getCash()));
        nextLandmarkValueLabel.setText(player.getNextLandmarkName());
        lastLandmarkValueLabel.setText(player.getLastLandmarkName());
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

    public void backToMainMenu() throws IOException {
        Stage currentStage = (Stage) carImageView.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Moose and Vamoose");
        stage.setScene(scene);
        stage.show();
        currentStage.close();
    }

    public void gameOver(String reason) {
        cancelTick();
        System.out.println(reason);
        gameOverLabel.setVisible(true);
        backToMainMenuButton.setVisible(true);
        backToMainMenuButton.setOnAction(event -> {
            try {
                backToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void gameVictory() {
        System.out.println("gameVictory() has been triggered"); //debug
        cancelTick();
        System.out.println("cancelTick() should have fired"); //debug

        //change game over label to victory label
        gameOverLabel.setText("You win!");
        gameOverLabel.setVisible(true);

        //main menu button, but fancied up
        backToMainMenuButton.setText("End game");
        backToMainMenuButton.setVisible(true);
        backToMainMenuButton.setOnAction(event -> {
            try {
                backToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void storePlayer(PlayerStats Player) {
        System.out.println("GameplayController storePlayer(player)");
        player = Player;
    }


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
                showExplosion();
                cancelTick();
                gameOver("Game Over.\n You hit a moose."); //replace later
                resetMooseEvent();
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
        //System.out.println("Moose has spawned"); //debug
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
        System.out.println("Moose event reset (has been de-spawned)"); //debug
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
        //System.out.println("animateMooseAtSpeed() fired");//DEBUG
    }

    @FXML
    void slowDownBtnClicked(ActionEvent event) {
        if ((player.getSpeed()-5) > 0) {
            double newSpeed = player.getSpeed() - 5;
            player.setSpeed(newSpeed);
            speedValueLabel.setText(String.valueOf((player.getSpeed())));
            //System.out.println(newSpeed);
        }
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

    @FXML
    public void showExplosion() {
        explosion.setVisible(true);
    }
}
