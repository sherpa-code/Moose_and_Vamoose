package game;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.RED;

import java.util.Timer;
import java.util.TimerTask;

public class LandmarkController {

    @FXML private Label dateValueLabel;
    @FXML private Label hungerValueLabel;
    @FXML private Label thirstValueLabel;
    @FXML private Label fuelValueLabel;
    @FXML private Label restroomValueLabel;
    @FXML private Label fatigueValueLabel;
    @FXML private Label nextLandmarkValueLabel;
    @FXML private Label cashValueLabel;
    @FXML private Button buyFuelButton;
    @FXML private Button buyFoodButton;
    @FXML private Button buyDrinkButton;
    @FXML private Button rentHotelButton;

    LandmarkController() {

    }

    /**
     * Fired when the .fxml and controller has loaded.
     * Begins the gameplay ticking.
     */
    public void initialize() {
        updatePlayerStatsLabels();
    }

    // buy buttons are not yet implemented, need to be handled in landmark scene
    @FXML
    void buyFuelBtnClicked(ActionEvent event) {
        System.out.println("buy fuel");
    }

    @FXML
    void buyFoodBtnClicked(ActionEvent event) {
        System.out.println("buy food");
    }

    @FXML
    void buyDrinkBtnClicked(ActionEvent event) {
        System.out.println("buy drink");
    }

    @FXML
    void rentHotelBtnClicked(ActionEvent event) {
        System.out.println("rent hotel");
    }

    @FXML
    void leaveLandmarkBtnClicked(ActionEvent event) {
        System.out.println("leave landmark");
        // TODO: must load back into the main gameplay loop and
        //  update the on-screen labels (might already work when Gameplay is implemented, need to confirm)
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
        dateValueLabel.setText(player.getCurrentDate().toString());
        nextLandmarkValueLabel.setText(player.getNextLandmarkName());
        cashValueLabel.setText(String.valueOf(player.getCash()));
    }
}
