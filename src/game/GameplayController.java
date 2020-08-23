package game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameplayController {
    //int imageViewForegroundVariation =
    int treeXTranslateResetDistance = 1300;
    double fuelConsumptionRate = 1.0;
    int tickRateMS = 10;
    public boolean mooseExists = false;
    Timer currentGameTickTimer;
    Timer carAnimationTimer;
    PlayerStats player = new PlayerStats( // default error values
            55, 55, 55, 55,0,
            55, 55, 1
    );
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
    double treeDeadImageViewBackground_1_Speed = 12;

    double treeConiferousImageViewBackground_1_Speed = 20;

    double treeConiferousImageViewForeground_1_Speed = 24;
    double treeDeciduousImageViewForeground_1_Speed = 32;
    double treeConiferousImageViewForeground_2_Speed = 16;
    double treeDeciduousImageViewForeground_2_Speed = 40;
    double treeConiferousImageViewForeground_3_Speed = 26;
    double treeDeciduousImageViewForeground_3_Speed = 22;
    double treeConiferousImageViewForeground_4_Speed = 44;
    double treeDeciduousImageViewForeground_4_Speed = 28;
    double treeConiferousImageViewForeground_5_Speed = 12;



    @FXML private Label hungerValueLabel;
    @FXML private Label thirstValueLabel;
    @FXML private Label fuelValueLabel;
    @FXML private Label fatigueValueLabel;
    @FXML private Label nextLandmarkValueLabel;
    @FXML private Label lastLandmarkValueLabel;
    @FXML private Label distanceTraveledValueLabel;
    @FXML private Label speedValueLabel;
    @FXML private Label cashValueLabel;
    @FXML private Label restroomValueLabel;
    @FXML private Label dateValueLabel;
    @FXML public ImageView moose;
    @FXML public ImageView carImageView;
    @FXML public GridPane insertPane;
    @FXML public Button avoidButton;
    @FXML public Label gameOverLabel;
    @FXML public Label gameOverReasonLabel;
    @FXML public Button backToMainMenuButton;
    @FXML public ImageView explosion;
    @FXML public Button speed50Button;
    @FXML public Button speed100Button;
    @FXML public Button speedUpButton;
    @FXML public Button slowDownButton;
    @FXML public GridPane backgroundGridPane;

    @FXML public ImageView treeDeadImageViewForeground;
    @FXML public ImageView treeDeciduousImageViewBackground;
    @FXML public ImageView treeConiferousImageViewBackground;

    @FXML public ImageView treeDeciduousImageViewForeground;
    @FXML public ImageView treeDeciduousImageViewForeground2;
    @FXML public ImageView treeDeciduousImageViewForeground3;
    @FXML public ImageView treeDeciduousImageViewForeground4;

    @FXML public ImageView treeConiferousImageViewForeground;
    @FXML public ImageView treeConiferousImageViewForeground2;
    @FXML public ImageView treeConiferousImageViewForeground3;
    @FXML public ImageView treeConiferousImageViewForeground4;
    @FXML public ImageView treeConiferousImageViewForeground5;


    public GameplayController() throws ParseException {}
    public void main(String[] args) {}

    /**
     * Fired when the .fxml and controller has loaded.
     * Begins the gameplay ticking.
     */
    public void initialize() {
        cashValueLabel.setText(String.valueOf(player.getCash()));
        beginTick();
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

                if (player.getSpeed() > 0) { // animates the car during motion
                    rumbleCar();
                    //animateTrees();
                    animateAllTrees();
                }
                if (mooseExists) {
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
                    if (player.getSpeed() > 0 && mooseSpawnRoll()) {
                        activateMooseEvent();
                    }
                }
            });
            }
        };
        currentGameTickTimer.scheduleAtFixedRate(task, 0, tickRateMS);
    }

    /**
     * check that the player traveled the necessary distance to reach the next landmark
     * @return if has the player traveled the necessary distance to reach the next landmark
     */
    public boolean reachedLandmark() {
        if (player.getDistanceTraveled() >=
                Integer.parseInt(player.landmarkAttributes[player.getLastLandmarkIndex()+1][1])) {
            return true;
        }
        return false;
    }

    /**
     *
     * @throws IOException
     */
    public void loadNextLandmarkScene() throws IOException {
        Stage currentStage = (Stage) carImageView.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Landmark.fxml"));
        Parent root = loader.load();
        LandmarkController landmarkController = loader.getController();
        landmarkController.storePlayer(player);
        landmarkController.storeSavingObj(savingObj);
        landmarkController.updateLandmarkStatsLabels(player);
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("You are at a Landmark. Make the right choice!");
        stage.setScene(scene);

        stage.show();
        currentStage.close();
    }

    /**
     * has a chance to spawn a moose
     * @return if a moose was spawned by this
     */
    public Boolean mooseSpawnRoll() {
        Random random = new Random();
        Float roll = random.nextFloat();

        return (roll <= 0.00075f && !mooseExists);

    }

    /**
     * Sets the player object's new stat values,
     * ensures the values are within the range 0 to 100,
     * checks if a Game Over state has been reached and ends game if so, and
     * updates the corresponding Labels in the UI.
     *
     * @param player the PlayerStats object
     */
    public void updatePlayerStats(PlayerStats player) {
        if (player.getSpeed() <= 110) { // Player burns 15% more fuel traveling over 110kmh
            player.setFuel(player.getFuel() - player.getFuelRate() * player.getSpeed());
        } else {
            player.setFuel(player.getFuel() - player.getFuelRate() * player.getSpeed() * 1.15);
        }

        player.setHunger(player.getHunger() + player.getHungerRate());
        savingObj[0][1] = String.valueOf(player.getHunger());

        player.setThirst(player.getThirst() + player.getThirstRate());
        savingObj[1][1] = String.valueOf(player.getThirst());

        savingObj[2][1] = String.valueOf(player.getFuel());

        player.setFatigue(player.getFatigue() + player.getFatigueRate());
        savingObj[3][1] = String.valueOf(player.getFatigue());

        savingObj[4][1] = String.valueOf(player.getSpeed());

        player.setDistanceTraveled(player.getDistanceTraveled() + player.getSpeed()/100000); // controls the ratio between distance traveled and speed
        savingObj[5][1] = String.valueOf(player.getDistanceTraveled());

        savingObj[6][1] = String.valueOf(player.getCash());

        savingObj[7][1] = String.valueOf(player.getLastLandmarkIndex());

        player.clampPlayerStats();
        updatePlayerStatsLabels(player);
        checkIfGameOverFromStats(); // Check if game over from a player stat reaching a critical level
    }

    /**
     * A method that evaluates player's stats at a time and checks if they are beyond critical acceptable values
     * Causes game over due to extreme conditions raised by current stats values
     */
    public void checkIfGameOverFromStats(){
        if (player.getFuel() <=0) {
            gameOver("You ran out of fuel.");
        } else if (player.getHunger() >= 100) {
            gameOver("You starved.");
        } else if (player.getThirst() >= 100) {
            gameOver("You fainted of thirst.");
        } else if (player.getFatigue() >= 100) {
            gameOver("You fell asleep.");
        }
    }

    /**
     * A method that updates player status labels to current running values, truncated (via casting) to an integer
     *
     * @param player as an instance of PlayerStats class and gets its properties
     */
    public void updatePlayerStatsLabels(PlayerStats player) {
        hungerValueLabel.setText(String.valueOf((int) player.getHunger()));
        thirstValueLabel.setText(String.valueOf((int) player.getThirst()));
        fuelValueLabel.setText(String.valueOf((int) player.getFuel()));
        //restroomValueLabel.setText("0");
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
        currentGameTickTimer.cancel();
        currentGameTickTimer.purge();
    }


    /**
     * Returns to the Main Menu scene after closing the current scene/stage.
     * @throws IOException
     */
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


    /**
     * Stops the game update tick and
     * displays the Game Over label and Back to Main Menu button
     * @param reason
     */
    public void gameOver(String reason) {
        cancelTick();
        speed50Button.setDisable(true);
        speed100Button.setDisable(true);
        speedUpButton.setDisable(true);
        slowDownButton.setDisable(true);
        gameOverLabel.setVisible(true);
        gameOverReasonLabel.setText(reason);
        gameOverReasonLabel.setVisible(true);
        backToMainMenuButton.setVisible(true);
        backToMainMenuButton.setOnAction(event -> {
            try {
                backToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Stops the game update tick,
     * sets the existing (but invisible) game over label to "You win!",
     * displays the label, and
     * displays the Back to Main Menu button
     */
    public void gameVictory() {
        cancelTick();
        speed50Button.setDisable(true);
        speed100Button.setDisable(true);
        speedUpButton.setDisable(true);
        slowDownButton.setDisable(true);

        gameOverLabel.setText("You win!");
        gameOverLabel.setVisible(true);

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

    /**
     * allows another Controller to pass in the existing player object
     * @param Player
     */
    public void storePlayer(PlayerStats Player) {
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
        double mooseXPosMax = moose.getBoundsInParent().getMaxX();
        double carXPosMin = car.getBoundsInParent().getMinX();
        return (mooseXPosMax >= carXPosMin);
    }

    /**
     * Removes the spawned moose from the screen when Avoid button is clicked
     */
    @FXML
    public void avoidButtonClicked() {
        mooseExists = false;
        resetMooseEvent();
    }

    /**
     * fired once for each tick while the moose is active
     */
    @FXML
    public void tickMoose() {
        if (mooseExists) {
            if (checkIfMooseCollision(moose, carImageView)) {
                showExplosion();
                gameOver("You hit a moose.");
                resetMooseEvent();
            } else {
                animateMoose();
            }
        }
    }


    /**
     * begins the moose event, sets it to an active state
     */
    @FXML
    public void activateMooseEvent() {
        mooseExists = true;
        //moose.setTranslateX(-80);
        moose.setTranslateX(0); // set to 0 to develop spawning the tree ImageView
        moose.setVisible(true);
        double newButtonPositionX = 300 + (Math.random() * 100);
        avoidButton.setVisible(true);
        avoidButton.setTranslateX(newButtonPositionX);



//        Image image = new Image("file:img/treeConiferousSprite.png");
//        ImageView imageView = new ImageView(image);
//        backgroundGridPane.getChildren().add(imageView);
//        imageView.setVisible(true);
//        imageView.setTranslateX(moose.getTranslateX());
//        imageView.setTranslateY(moose.getTranslateY());



        //imageView.setTranslateX(newButtonPositionX);
        //backgroundGridPane.add(new Image("file:img/treeConiferousSprite.png"));
        //Image image = new Image("FIle:img/treeConiferousSprite.png");


        //TODO: set the Y positions as well, but within a range of the default
        // - so save the value first, update the translate
        // then set it back to the saved
    }

    /**
     * resets the moose event to its original state
     */
    @FXML
    public void resetMooseEvent() {
        mooseExists = false;

        moose.setTranslateX(0);
        moose.setVisible(false);

        avoidButton.setTranslateX(100);
        avoidButton.setVisible(false);
    }


    /**
     * animates moose relative to player's current speed
     */
    @FXML
    public void animateMoose() {
        double mooseXPosition = moose.getTranslateX();
        moose.setTranslateX(mooseXPosition + (player.getSpeed() / 25));
    }

    /**
     * animates moose relative to player's current speed
     */
    @FXML
    public void animateTrees() {
        // TODO: set the tree passing speed to have
        //  some variation to imply that trees are not perfectly in a line as you drive by
        double treeDeciduousXPosition = treeDeciduousImageViewForeground.getTranslateX();
        double treeConiferousXPosition = treeConiferousImageViewBackground.getTranslateX();
        //TODO: add a random number gen here and pass that in instead of the denominator below to satisfy above?
        treeDeciduousImageViewForeground.setTranslateX(treeDeciduousXPosition + (player.getSpeed() / 4));
        //treeConiferousImageView.setTranslateX(treeXPosition + (player.getSpeed() / 18));
        treeConiferousImageViewBackground.setTranslateX(treeConiferousXPosition + (player.getSpeed() / 12));
    }

    /**
     * animates moose relative to player's current speed
     */
    @FXML
    public void animateAllTrees() {
        // TODO: set the tree passing speed to have
        //  some variation to imply that trees are not perfectly in a line as you drive by
        double treeDeadForeground_1_XPosition = treeDeadImageViewForeground.getTranslateX();
        //System.out.println(treeDeadForeground_1_XPosition);
        if (treeDeadForeground_1_XPosition > treeXTranslateResetDistance) {
            treeDeadForeground_1_XPosition = -250;
        }
//
//        double treeDeciduousBackground_1_XPosition = treeDeciduousImageViewBackground.getTranslateX(); // TODO: doesnt exist yet in FXML

        double treeConiferousBackground_1_XPosition = treeConiferousImageViewBackground.getTranslateX();
        if (treeConiferousBackground_1_XPosition > treeXTranslateResetDistance) {
            treeConiferousBackground_1_XPosition = -500;
        }

        double treeDeciduousForeground_1_XPosition = treeDeciduousImageViewForeground.getTranslateX();
        double treeDeciduousForeground_2_XPosition = treeDeciduousImageViewForeground2.getTranslateX();
        double treeDeciduousForeground_3_XPosition = treeDeciduousImageViewForeground3.getTranslateX();
        double treeDeciduousForeground_4_XPosition = treeDeciduousImageViewForeground4.getTranslateX();
        if (treeDeciduousForeground_1_XPosition > treeXTranslateResetDistance) {
            treeDeciduousForeground_1_XPosition = -300;
        }
        if (treeDeciduousForeground_2_XPosition > treeXTranslateResetDistance) {
            treeDeciduousForeground_2_XPosition = -300;
        }
        if (treeDeciduousForeground_3_XPosition > treeXTranslateResetDistance) {
            treeDeciduousForeground_3_XPosition = -300;
        }
        if (treeDeciduousForeground_4_XPosition > treeXTranslateResetDistance) {
            treeDeciduousForeground_4_XPosition = -300;
        }

        double treeConiferousForeground_1_XPosition = treeConiferousImageViewForeground.getTranslateX();
        double treeConiferousForeground_2_XPosition = treeConiferousImageViewForeground2.getTranslateX();
        double treeConiferousForeground_3_XPosition = treeConiferousImageViewForeground3.getTranslateX();
        double treeConiferousForeground_4_XPosition = treeConiferousImageViewForeground4.getTranslateX();
        double treeConiferousForeground_5_XPosition = treeConiferousImageViewForeground5.getTranslateX();
        if (treeConiferousForeground_1_XPosition > treeXTranslateResetDistance) {
            treeConiferousForeground_1_XPosition = -440;
        }
        if (treeConiferousForeground_2_XPosition > treeXTranslateResetDistance) {
            treeConiferousForeground_2_XPosition = -430;
        }
        if (treeConiferousForeground_3_XPosition > treeXTranslateResetDistance) {
            treeConiferousForeground_3_XPosition = -460;
        }
        if (treeConiferousForeground_4_XPosition > treeXTranslateResetDistance) {
            treeConiferousForeground_4_XPosition = -430;
        }
        if (treeConiferousForeground_5_XPosition > treeXTranslateResetDistance) {
            treeConiferousForeground_5_XPosition = -430;
        }

        //TODO: add a random number gen here and pass that in instead of the denominator below to satisfy above?
        treeDeadImageViewForeground.setTranslateX(treeDeadForeground_1_XPosition + (player.getSpeed() / treeDeadImageViewBackground_1_Speed));

//        treeDeciduousImageViewBackground.setTranslateX(treeDeciduousBackground_1_XPosition + (player.getSpeed()) / 14); // TODO: enable once implemented in fXML

        treeConiferousImageViewBackground.setTranslateX(treeConiferousBackground_1_XPosition + (player.getSpeed()) / treeConiferousImageViewBackground_1_Speed);

        treeDeciduousImageViewForeground.setTranslateX(treeDeciduousForeground_1_XPosition + (player.getSpeed()) / treeDeciduousImageViewForeground_1_Speed);
        treeDeciduousImageViewForeground2.setTranslateX(treeDeciduousForeground_2_XPosition + (player.getSpeed()) / treeDeciduousImageViewForeground_2_Speed);
        treeDeciduousImageViewForeground3.setTranslateX(treeDeciduousForeground_3_XPosition + (player.getSpeed()) / treeDeciduousImageViewForeground_3_Speed);
        treeDeciduousImageViewForeground4.setTranslateX(treeDeciduousForeground_4_XPosition + (player.getSpeed()) / treeDeciduousImageViewForeground_4_Speed);

        treeConiferousImageViewForeground.setTranslateX(treeConiferousForeground_1_XPosition + (player.getSpeed()) / treeConiferousImageViewForeground_1_Speed);
        treeConiferousImageViewForeground2.setTranslateX(treeConiferousForeground_2_XPosition + (player.getSpeed()) / treeConiferousImageViewForeground_2_Speed);
        treeConiferousImageViewForeground3.setTranslateX(treeConiferousForeground_3_XPosition + (player.getSpeed()) / treeConiferousImageViewForeground_3_Speed);
        treeConiferousImageViewForeground4.setTranslateX(treeConiferousForeground_4_XPosition + (player.getSpeed()) / treeConiferousImageViewForeground_4_Speed);
        treeConiferousImageViewForeground5.setTranslateX(treeConiferousForeground_5_XPosition + (player.getSpeed()) / treeConiferousImageViewForeground_5_Speed);


    }





//    public void updateAllTreePositions() {
//        double treeDeadForeground_1_XPosition = treeDeadImageViewForeground.getTranslateX();
//
//        double treeDeciduousBackground_1_XPosition = treeDeciduousImageViewBackground.getTranslateX();
//
//        double treeConiferousBackground_1_XPosition = treeConiferousImageViewBackground.getTranslateX();
//
//        double treeDeciduousForeground_1_XPosition = treeDeciduousImageViewForeground.getTranslateX();
//        double treeDeciduousForeground_2_XPosition = treeDeciduousImageViewForeground2.getTranslateX();
//        double treeDeciduousForeground_3_XPosition = treeDeciduousImageViewForeground3.getTranslateX();
//        double treeDeciduousForeground_4_XPosition = treeDeciduousImageViewForeground4.getTranslateX();
//
//        double treeConiferousForeground_1_XPosition = treeConiferousImageViewForeground.getTranslateX();
//        double treeConiferousForeground_2_XPosition = treeConiferousImageViewForeground2.getTranslateX();
//        double treeConiferousForeground_3_XPosition = treeConiferousImageViewForeground3.getTranslateX();
//        double treeConiferousForeground_4_XPosition = treeConiferousImageViewForeground4.getTranslateX();
//        double treeConiferousForeground_5_XPosition = treeConiferousImageViewForeground5.getTranslateX();
//    }
//
//
//    public void setAllTreeTranslates() {
//        //TODO: add a random number gen here and pass that in instead of the denominator below to satisfy above?
//        treeDeciduousImageViewForeground.setTranslateX(treeDeciduousXPosition + (player.getSpeed() / 4));
//        //treeConiferousImageView.setTranslateX(treeXPosition + (player.getSpeed() / 18));
//        treeConiferousImageViewBackground.setTranslateX(treeConiferousXPosition + (player.getSpeed() / 12));
//
//
//
//        treeDeadImageViewForeground.setTranslateX(treeDeadForeground_1_XPosition + (player.getSpeed() / 4));
//
//    }

    /**
     * sets the player speed and updates relevant labels
     * @param targetSpeed the speed to that the player will now travel
     */
    @FXML
    void setSpeedToValue(Integer targetSpeed) {
        player.setSpeed(targetSpeed);
        speedValueLabel.setText(String.valueOf(player.getSpeed()));
    }

    /**
     * sets the player's speed attribute to 50
     */
    @FXML
    void speed50BtnClicked() {
        setSpeedToValue(50);
    }

    /**
     * sets the player's speed attribute to 100
     */
    @FXML
    void speed100BtnClicked() {
        setSpeedToValue(100);
    }

    /**
     * lowers the player's speed attribute by 5, within acceptable bounds
     */
    @FXML
    void slowDownBtnClicked() {
        if ((player.getSpeed()-5) > 0) {
            setSpeedToValue((int) player.getSpeed() - 5);
        }
    }

    /**
     * increasess the player's speed attribute by 5, within acceptable bounds
     */
    @FXML
    void speedUpBtnClicked() {
        if ((player.getSpeed()+5) <= 250) {
            setSpeedToValue((int) player.getSpeed() + 5);
        }
    }

    /**
     * helps in creating an animation of the moving car
     * //TODO: new function to handle smoother animation
     */
    @FXML
    public void rumbleCar() {
        if (carImageView.getRotate() != 1) {
            carImageView.setRotate(1);
        } else {
            carImageView.setRotate(-1);
        }
    }

    /**
     * sets the explosion sprite to visible;
     * happens when moose collision occurs.
     */
    @FXML
    public void showExplosion() {
        explosion.setVisible(true);
    }

    public PlayerStats getPlayer() {
        return player;
    }
}
