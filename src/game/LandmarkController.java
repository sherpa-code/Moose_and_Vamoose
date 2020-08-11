package game;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import static java.lang.Integer.parseInt;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.RED;

import java.util.Timer;
import java.util.TimerTask;

public class LandmarkController {

    @FXML    private Label hungerLabel;
    @FXML    private Label thirstLabel;
    @FXML    private Label fuelLabel;
    @FXML    private Label fatigueLabel;
    @FXML    private Label cashLabel;

    @FXML    private Label hungerValueLabel;
    @FXML    private Label thirstValueLabel;
    @FXML    private Label fuelValueLabel;
    @FXML    private Label fatigueValueLabel;
    @FXML    private Label cashValueLabel; // OK

    @FXML    private Label landmarkFuelCostLabel;
    @FXML    private Label advertisedFuelForLabel;
    @FXML    private Label advertisedFuelLabel;
    @FXML    private Label landmarkFuelQuantity;

    @FXML    private Label landmarkHotelCostLabel;
    @FXML    private Label advertisedFatigueForLabel;
    @FXML    private Label advertisedFatigueLabel;
    @FXML    private Label landmarkFatigueQuantity; // OK 2

    @FXML    private Label landmarkFoodCostLabel;
    @FXML    private Label advertisedHungerForLabel;
    @FXML    private Label advertisedHungerLabel;
    @FXML    private Label landmarkFoodQuantity; // OK 3

    @FXML    private Label drinkCostLabel;
    @FXML    private Label advertisedThirstForLabel;
    @FXML    private Label advertisedThirstLabel;
    @FXML    private Label landmarkThirstQuantity; // OK 4

    @FXML    private Button saveGameButton;
    @FXML    private Button buyFuelButton;
    @FXML    private Button buyDrinkButton;
    @FXML    private Button buyHungerButton;
    @FXML    private Button rentHotelButton;
    @FXML    private Button departLandmarkButton;
    @FXML    private ImageView carImageView;
    @FXML    private ImageView hotelImageView;
    @FXML    private ImageView storeImageView;
    @FXML    private ImageView gasStationImageView;
    @FXML    private Label landmarkNameLabel;
    @FXML    private Label landmarkSizeLabel;
    @FXML    private Label landmarkInsufficientFundsLabel;

    private PlayerStats player;


    /**
     * A method that updates player status labels to current running values, truncated (via casting) to an integer
     *
     * @param player as an instance of PlayerStats class and gets its properties
     */
    //TODO: Should updates stats of the player during a time period (per second?!)
    public void updateLandmarkStatsLabels(PlayerStats player) {
        System.out.println("hunger = "+player.getHunger());
        hungerValueLabel.setText(String.valueOf((int) player.getHunger()));
        thirstValueLabel.setText(String.valueOf((int) player.getThirst()));
        fuelValueLabel.setText(String.valueOf((int) player.getFuel()));
        fatigueValueLabel.setText(String.valueOf((int) player.getFatigue()));
        cashValueLabel.setText(String.valueOf(player.getCash()));
        landmarkNameLabel.setText(player.getNextLandmarkName());
        landmarkSizeLabel.setText(player.getNextLandmarkSize());
        //player.landmarkAttributes[player.getLastLandmarkIndex()+1][2]);
    }


    private void setLandmarkVisibility(){
        String landmarkSize = player.landmarkAttributes[player.getLastLandmarkIndex()+1][2];
        System.out.println(landmarkSize);
        if (landmarkSize == "large") {
            hotelImageView.setVisible(true);
            gasStationImageView.setVisible(true);
            storeImageView.setVisible(false);

        }
        else if(landmarkSize == "small"){
            storeImageView.setVisible(true);
            hotelImageView.setVisible(false);
            gasStationImageView.setVisible(false);

            rentHotelButton.setVisible(false);
            landmarkHotelCostLabel.setVisible(false);
            landmarkFatigueQuantity.setVisible(false);
            advertisedFatigueForLabel.setVisible(false);
            advertisedFatigueLabel.setVisible(false);

            buyFuelButton.setVisible(false);
            landmarkFuelCostLabel.setVisible(false);
            landmarkFuelQuantity.setVisible(false);
            advertisedFuelForLabel.setVisible(false);
            advertisedFuelLabel.setVisible(false);
        }
        else {
            System.out.println("Something went wrong here for background image");
        }
    }

    public void storePlayer(PlayerStats Player) {
        System.out.println("LandmarkController storePlayer(player)");
        player = Player;
    }

    @FXML
    void saveGameAtLandmarkClicked(ActionEvent event) {
        System.out.println("saveGameAtLandmarkClicked()");
//        String landmarkSize = player.landmarkAttributes[player.getLastLandmarkIndex()+1][2];
//        System.out.println(landmarkSize);
        setLandmarkVisibility();
    }


    @FXML
    void buyDrinkAtLandmarkClicked(ActionEvent event) {
        System.out.println("buyDrinkAtLandmarkClicked()");
//        System.out.println(player.getThirst());
////        System.out.println("Drink is ready");
//        System.out.println("Current cash: " + player.getCash());
        //System.out.println(String.valueOf(Double.parseDouble(landmarkDrinkCostLabel.getText())));
        if (player.getCash() < 3) {
            System.out.println("Sorry! Not enough money");
            landmarkInsufficientFundsLabel.setVisible(true);
            buyDrinkButton.setVisible(false);

        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 3);

            if( player.getThirst() - Double.parseDouble(landmarkThirstQuantity.getText()) < 0 ) {
                player.setThirst(0.0);
            }
            else {
                player.setThirst(
                        player.getThirst() - Double.parseDouble(landmarkThirstQuantity.getText())
                );

            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            thirstValueLabel.setText(String.valueOf((int) player.getThirst()));
//            System.out.println("Enjoy! You got a can of Drink");
//            System.out.println("Current cash: " + player.getCash());
//            System.out.println(player.getThirst());
        }

    }

    @FXML
    void buyFoodAtLandmarkClicked(ActionEvent event) {
        System.out.println("buyFoodAtLandmarkClicked(");
//        System.out.println("Current cash: " + player.getCash());
//        System.out.println("Hunger: "+player.getHunger());
        if (player.getCash() < 12) {
            System.out.println("Sorry! Not enough money");
            landmarkInsufficientFundsLabel.setVisible(true);
            buyHungerButton.setVisible(false);
        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 12);

            if( player.getHunger() - Double.parseDouble(landmarkFoodQuantity.getText()) < 0 ) {
                player.setHunger(0.0);
            }
            else {
                player.setHunger(
                        player.getHunger() - Double.parseDouble(landmarkFoodQuantity.getText())
                );
                //cashValueLabel.setText(String.valueOf(player.getCash()));
            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            hungerValueLabel.setText(String.valueOf((int) player.getHunger()));
//            System.out.println("Enjoy your meal! ");
//            System.out.println("Current cash: " + player.getCash());
//            System.out.println(player.getHunger());
        }

    }

    @FXML
    void buyFuelAtLandmarkClicked(ActionEvent event) {
        System.out.println("buyFuelAtLandmarkClicked()");
//        System.out.println("Current cash: " + player.getCash());
//        System.out.println(player.getFuel());
        if (player.getCash() < 20) {
            System.out.println("Sorry! Not enough money");
            landmarkInsufficientFundsLabel.setVisible(true);
            buyFuelButton.setVisible(false);
        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 20);

            if( player.getFuel() + Double.parseDouble(landmarkFuelQuantity.getText()) >= 100.00 ) {
                player.setFuel(100.0);
            }
            else {
                player.setFuel(
                        player.getFuel() + Double.parseDouble(landmarkFuelQuantity.getText())
                );

            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            fuelValueLabel.setText(String.valueOf((int) player.getFuel()));
//            System.out.println("Gas is filled up! ");
//            System.out.println("Current cash: " + player.getCash());
//            System.out.println(player.getFuel());
        }

    }

    @FXML
    void rentHotelRoomAtLandmarkClicked(ActionEvent event) {
        System.out.println("rentRoomAtLandmarkClicked()");
//        System.out.println("Current cash: " + player.getCash());
//        System.out.println(player.getFatigue());
        if (player.getCash() < 125) {
            System.out.println("Sorry! Not enough money");
            landmarkInsufficientFundsLabel.setVisible(true);
            rentHotelButton.setVisible(false);
        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 125);

            if( player.getFatigue() - Double.parseDouble(landmarkFatigueQuantity.getText()) <= 0.0 ) {
                player.setFatigue(0.0);
            }
            else {
                player.setFatigue(
                        player.getFatigue() - Double.parseDouble(landmarkFatigueQuantity.getText())
                );

            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            fatigueValueLabel.setText(String.valueOf((int) player.getFatigue()));
//            System.out.println("Have a great night in our Hotel!");
//            System.out.println("Current cash: " + player.getCash());
//            System.out.println(player.getFatigue());
        }

    }


    @FXML
    void departCurrentLandmarkClicked(ActionEvent event) throws IOException {
        System.out.println("departCurrentLandmarkClicked()");

        Stage currentStage = (Stage) departLandmarkButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gameplay.fxml"));
        Parent root = (Parent) loader.load();
        GameplayController gameplayController = loader.getController();
        //gameplayController.storePlayer(player);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("You are at a Landmark. Make the right choice!");
        stage.setScene(scene);

//        gameplayController.player.setFuel(player.getFuel());
//        gameplayController.player.setHunger(player.getHunger());
//        gameplayController.player.setThirst(player.getThirst());
//        gameplayController.player.setFatigue(player.getFatigue());
//        gameplayController.player.setCash(player.getCash());
        gameplayController.getPlayer().setFuel(player.getFuel());
        gameplayController.getPlayer().setHunger(player.getHunger());
        gameplayController.getPlayer().setThirst(player.getThirst());
        gameplayController.getPlayer().setFatigue(player.getFatigue());
        gameplayController.getPlayer().setCash(player.getCash());

        gameplayController.updatePlayerStatsLabels(player);

//        gameplayController.player.setFuel(Double.parseDouble(this.fuelValueLabel.getText()));
//        gameplayController.player.setHunger(Double.parseDouble(this.hungerValueLabel.getText()));
//        gameplayController.player.setThirst(Double.parseDouble(this.thirstValueLabel.getText()));
//        gameplayController.player.setFatigue(Double.parseDouble(this.fatigueValueLabel.getText()));
//        gameplayController.player.setCash(Double.parseDouble(this.fatigueValueLabel.getText()));

        stage.show();
        currentStage.close();

    }


}
