package game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameplayController {
    /**
     * Simply instantiation of a player object
     */

    double fuelConsumptionRate = 1.0;
    PlayerStats player = new PlayerStats(0, 0,50 * fuelConsumptionRate, 0, 0,40,450, " ");

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


        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        dateValueLabel.setText(dateFormat.format(player.getCurrentDate()));
        nextLandmarkValueLabel.setText(player.getNextLandMark());
        kmTraveledValueLabel.setText(String.valueOf(player.getDistanceTraveled()));

    }

    @FXML
    void slowDownBtnClicked(ActionEvent event) {
        int newSpeed = player.getCarSpeed()-5;
        //System.out.println(newSpeed);
        player.setCarSpeed(newSpeed);
        speedValueLabel.setText(String.valueOf((player.getCarSpeed())));

    }

    @FXML
    void speedUpBtnClicked(ActionEvent event) {
        int newSpeed = player.getCarSpeed()+5;
        //System.out.println(newSpeed);
        player.setCarSpeed(newSpeed);
        speedValueLabel.setText(String.valueOf((player.getCarSpeed())));
        if (newSpeed > 75) {
            //TODO: Fuel should get consumed in faster rate
        }


    }

    //Should increase fuel consumption with higher speeds
    void highFuelConsumption(){
        fuelConsumptionRate = 0.75;

    }

    //checks player's stats over time and changes the properties over time
    void reloadPlayersStats(){
        player.setFatigue(player.getFatigue()+5);
        player.setHunger(player.getHunger()+5);
        player.setThirst(player.getThirst()+5);
        player.setRestroom(player.getRestroom()+5);
        //And so on

    }

    void statsGettingChanges(){
        // TODO: A method that runs over time and changes player's stats
    }

    /**
     * Method that runs the properties of "GameplayController" class when it is called by "Main" app
     */
    public void initialize() {
        playersCurrentStatus(player);
    }
}
