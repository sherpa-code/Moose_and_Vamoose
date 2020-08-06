package game;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.RED;

public class GameplayController {
    /**
     * Simply instantiation of a player object
     */


    double fuelConsumptionRate = 1.0;
    PlayerStats player = new PlayerStats(0, 0, 100, 0, 0, 40, 450, " ");

    @FXML
    private Label dateLabel;

    @FXML
    private Label hungerLabel;

    @FXML
    private Label thirstLabel;

    @FXML
    private Label fuelLabel;

    @FXML
    private Label restroomLabel;

    @FXML
    private Label fatigueLabel;

    @FXML
    private Label nextLandmarkLabel;

    @FXML
    private Label kmTraveledLabel;

    @FXML
    private Label dateValueLabel;

    @FXML
    private Label hungerValueLabel;

    @FXML
    private Label thirstValueLabel;

    @FXML
    private Label fuelValueLabel;

    @FXML
    private Label restroomValueLabel;

    @FXML
    private Label fatigueValueLabel;

    @FXML
    private Label nextLandmarkValueLabel;

    @FXML
    private Label kmTraveledValueLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private Label speedValueLabel;

    @FXML
    private Button speedUpButton;

    @FXML
    private Button slowDownButton;

    @FXML
    public ImageView moose;

    @FXML
    public ImageView carImageView;


    public GameplayController() throws ParseException, IOException, ClassNotFoundException {
    }

    /**
     * A method that updates player status labels to current running values
     *
     * @param player as an instance of PlayerStats class and gets its properties
     */

    //TODO: Should updates stats of the player during a time period (per second?!)
    public void playersCurrentStatus(PlayerStats player) {
        hungerValueLabel.setText(String.valueOf((player.getHunger())));
        thirstValueLabel.setText(String.valueOf(player.getThirst()));
        fuelValueLabel.setText(String.valueOf(player.getFuel()));
        restroomValueLabel.setText(String.valueOf(player.getRestroom()));
        fatigueValueLabel.setText(String.valueOf(player.getFatigue()));
        speedValueLabel.setText(String.valueOf((player.getCarSpeed())));

        //TODO: Date stats label should be updated later on the screen
        //dateValueLabel.setText(player.getCurrentDate());
        nextLandmarkValueLabel.setText(player.getNextLandMark());
        kmTraveledValueLabel.setText(String.valueOf(player.getDistanceTraveled()));

    }


    /**
     * checks if car has collided with an 850lb ham on stilts
     *
     * @param moose moose object
     * @param car   car object
     * @return true if horrific auto accident, false otherwise
     */
    public Boolean checkIfMooseCollision(ImageView moose, ImageView car) {

        // calculate movement/collision bounds of the moose
        double mooseXPosMax = moose.getBoundsInParent().getMaxX();
        double carXPosMin = car.getBoundsInParent().getMinX();
        return (mooseXPosMax >= carXPosMin);
    }

    /**
     * creates an Avoid Button for random placement
     *
     * @return avoidButton
     */

    protected Button createAvoidButton() {
        Button avoidButton = new Button();
        avoidButton.setText("AVOID");
        avoidButton.setStyle("-fx-background-color: #ff0000; ");
        return avoidButton;
    }

    /**
     * creates a moose image for placement elsewhere
     *
     * @return moose
     */

    private ImageView createMoose() {


        File mooseFile = new File("img/moose.png");
        Image mooseImage = new Image(mooseFile.toURI().toString());
        ImageView moose = new ImageView();
        moose.setImage(mooseImage);
        return moose;
    }

    /**
     * placeholder function
     * needs to create objects, place moose on fxml line, start anim
     */
    public void initializeMooseInteraction() {
        ImageView moose = createMoose();
        Button avoidButton = createAvoidButton();

        //TODO clean insertions into FXML, need to figure out pathing for anim

    }


    @FXML
    public void animateMoose(ActionEvent event) {
        double mooseXPosition = moose.getTranslateX();
        moose.setTranslateX(mooseXPosition + 10);
        System.out.println("animateMoose() fired");//DEBUG
        if (checkIfMooseCollision(moose, carImageView)) {
            System.out.println("COLLISION DETECTED");//DEBUG
        }
    }

    @FXML
    public void animateMooseAtCarSpeed(ActionEvent event) {
        double mooseXPosition = moose.getTranslateX();
        moose.setTranslateX(mooseXPosition + player.getCarSpeed());
        System.out.println("animateMooseAtCarSpeed() fired");//DEBUG
        if (checkIfMooseCollision(moose, carImageView)) {
            System.out.println("COLLISION DETECTED");//DEBUG
        }
    }

    @FXML
    void slowDownBtnClicked(ActionEvent event) {
        int newSpeed = player.getCarSpeed() - 5;
        //System.out.println(newSpeed);
        player.setCarSpeed(newSpeed);
        speedValueLabel.setText(String.valueOf((player.getCarSpeed())));

    }

    @FXML
    void speedUpBtnClicked(ActionEvent event) {
        int newSpeed = player.getCarSpeed() + 5;
        //System.out.println(newSpeed);
        player.setCarSpeed(newSpeed);
        speedValueLabel.setText(String.valueOf((player.getCarSpeed())));
        if (newSpeed > 75) {
            //TODO: Fuel should get consumed in faster rate
        }
    }

    //Should increase fuel consumption with higher speeds
    void highFuelConsumption() {
        fuelConsumptionRate = 0.75;
    }

    //checks player's stats over time and changes the properties over time
    void reloadPlayersStats() {
        player.setFatigue(player.getFatigue() + 5);
        player.setHunger(player.getHunger() + 5);
        player.setThirst(player.getThirst() + 5);
        player.setRestroom(player.getRestroom() + 5);
        //And so on

    }

    void statsGettingChanges() {
        // TODO: A method that runs over time and changes player's stats
    }

    /**
     * Method that runs the properties of "GameplayController" class when it is called by "Main" app
     */
    public void initialize() {
        playersCurrentStatus(player);

    }


}
