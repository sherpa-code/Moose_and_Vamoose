package game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GameplayController {
    /**
     * Simply instantiation of a player object
     */

    
    PlayerStats player1 = new PlayerStats(0, 0,100, 0, 0,0,450, " ");

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

    public GameplayController() throws ParseException, IOException, ClassNotFoundException {
    }

    /**
     * A method that updates player status labels to current running values
     * @param player as an instance of PlayerStats class and gets its properties
     */

    //TODO: Should updates stats of the player during a time period (per second?!)
    public void playersCurrentStatus (PlayerStats player) {
        hungerValueLabel.setText(String.valueOf((player.getHunger())));
        thirstValueLabel.setText(String.valueOf(player.getThirst()));
        fuelValueLabel.setText(String.valueOf(player.getFuel()));
        restroomValueLabel.setText(String.valueOf(player.getRestroom()));
        fatigueValueLabel.setText(String.valueOf(player.getFatigue()));
        speedValueLabel.setText(String.valueOf((player.getCarSpeed())));

        //TODO: Date stats label should be updated later on the screenGamm
        //dateValueLabel.setText(player.getCurrentDate());
        nextLandmarkValueLabel.setText(player.getNextLandMark());
        kmTraveledValueLabel.setText(String.valueOf(player.getDistanceTraveled()));

    }
    /**
     * Method that runs the properties of "GameplayController" class when it is called by "Main" app
     */
    public void initialize() {
        playersCurrentStatus(player1);
    }
}
