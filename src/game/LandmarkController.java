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

    //    @FXML private Label dateValueLabel;
//    @FXML private Label hungerValueLabel;
//    @FXML private Label thirstValueLabel;
//    @FXML private Label fuelValueLabel;
//    @FXML private Label fatigueValueLabel;
//    @FXML private Label nextLandmarkValueLabel;
//    @FXML private Label cashValueLabel;
//    @FXML private Label landmarkNameLabel;
//    @FXML private Button buyFuelButton;
//    @FXML private Button buyFoodButton;
//    @FXML private Button buyDrinkButton;
//    @FXML private Button rentHotelButton;
    @FXML    private Label hungerLabel;

    @FXML    private Label thirstLabel;
    @FXML    private Label fuelLabel;
    @FXML    private Label fatigueLabel;
    @FXML    private Label hungerValueLabel;
    @FXML    private Label thirstValueLabel;
    @FXML    private Label fuelValueLabel;
    @FXML    private Label fatigueValueLabel;
    @FXML    private Label cashLabel;
    @FXML    private Label cashValueLabel;
    @FXML    private Label landmarkFuelCostLabel;
    @FXML    private Label landmarkFoodCostLabel;
    @FXML    private Label landmarkDrinkCostLabel;
    @FXML    private Label landmarkHotelCostLabel;
    @FXML    private Label landmarkFuelQuantity;
    @FXML    private Label landmarkFoodQuantity;
    @FXML    private Label landmarkThirstQuantity;
    @FXML    private Label landmarkFatigueQuantity;
    @FXML    private Label advertisedFuelForLabel;
    @FXML    private Label advertisedFatigueForLabel;
    @FXML    private Label advertisedFuelLabel;
    @FXML    private Label advertisedThirstlabel;
    @FXML    private Label advertisedFatigueLabel;
    @FXML    private Label notEnoughMoneyLabel;
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

    public void storePlayer(PlayerStats Player) {
        System.out.println("test");
        player = Player;
    }
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

    @FXML
    void buyDrinkAtLandmarkClicked(ActionEvent event) {
        System.out.println("buyDrinkAtLandmarkClicked()");
//        System.out.println(player.getThirst());
////        System.out.println("Drink is ready");
//        System.out.println("Current cash: " + player.getCash());
        //System.out.println(String.valueOf(Double.parseDouble(landmarkDrinkCostLabel.getText())));
        if (player.getCash() < 3) {
            System.out.println("Sorry! Not enough money");
            notEnoughMoneyLabel.setVisible(true);
            buyDrinkButton.setVisible(false);

        } else {
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
            notEnoughMoneyLabel.setVisible(true);
            buyHungerButton.setVisible(false);
        } else {
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
            notEnoughMoneyLabel.setVisible(true);
            buyFuelButton.setVisible(false);
        } else {
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
            notEnoughMoneyLabel.setVisible(true);
            rentHotelButton.setVisible(false);
        } else {
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
        //landmarkController.updatePlayerStatsLabels(player);
        //gameplayController.updateLandmarkStatsLabels(player);

        //landmarkController.init(table.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("You are at a Landmark. Make the right choice!");
        stage.setScene(scene);
        //gameplayController.isNewPlayer = false;
        //gameplayController.testBoolean.setText("Changed!");
        gameplayController.player.setFuel(Double.parseDouble(this.fuelValueLabel.getText()));
        gameplayController.player.setHunger(Double.parseDouble(this.hungerValueLabel.getText()));
        gameplayController.player.setThirst(Double.parseDouble(this.thirstValueLabel.getText()));
        gameplayController.player.setFatigue(Double.parseDouble(this.fatigueValueLabel.getText()));
//        gameplayController.newCash = parseInt(this.cashValueLabel.getText());
//        System.out.println(gameplayController.newCash);
        //gameplayController.player.setCash(1000);
        //System.out.println(gameplayController.player.getSpeed());

        //gameplayController.player.setSpeed(0.0);
        //gameplayController.player.setDistanceTraveled(player.getDistanceTraveled());



        //System.out.println(gameplayController.isNewPlayer);
        stage.show();
        currentStage.close();

    }

    private void setLandmarkVisibility(){
        String landmarkSize = player.landmarkAttributes[player.getLastLandmarkIndex()+1][2];
//        String landmarkSize = "small";
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

    @FXML
    void saveGameAtLandmarkClicked(ActionEvent event) {
        System.out.println("saveGameAtLandmarkClicked()");
//        String landmarkSize = player.landmarkAttributes[player.getLastLandmarkIndex()+1][2];
//        System.out.println(landmarkSize);
        setLandmarkVisibility();



    }



    public void departLandmark(PlayerStats player) {
//        Stage currentStage = (Stage) carImageView.getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Landmark.fxml"));
//
//        Parent root = (Parent) loader.load();
//        LandmarkController landmarkController = loader.getController();
//        landmarkController.storePlayer(player);
//        //landmarkController.updatePlayerStatsLabels(player);
//        landmarkController.updateLandmarkStatsLabels(player);
//
//        //landmarkController.init(table.getSelectionModel().getSelectedItem());
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//
//        stage.setTitle("You are at a Landmark. Make the right choice!");
//        stage.setScene(scene);
//
//        System.out.println(landmarkController);
//
//        stage.show();
//        currentStage.close();
    }

//    public void initialize() {
//
//    }
//
//    public void storePlayer(PlayerStats Player) {
//        //player = Player;
//        //System.out.println("Player stored in Landmark");
//    }
//
//    LandmarkController() {
//
//    }
//
//    // buy buttons are not yet implemented, need to be handled in landmark scene
//    @FXML
//    void buyFuelBtnClicked(ActionEvent event) {
//        System.out.println("buy fuel");
//    }
//
//    @FXML
//    void buyFoodBtnClicked(ActionEvent event) {
//        System.out.println("buy food");
//    }
//
//    @FXML
//    void buyDrinkBtnClicked(ActionEvent event) {
//        System.out.println("buy drink");
//    }
//
//    @FXML
//    void rentHotelBtnClicked(ActionEvent event) {
//        System.out.println("rent hotel");
//    }
//
//    @FXML
//    public void saveGameBtnClicked(ActionEvent event) {
//
//    }
//
//    @FXML
//    void leaveLandmarkBtnClicked(ActionEvent event) {
//        System.out.println("leave landmark");
//        // TODO: must load back into the main gameplay loop and
//        //  update the
//    }
//
//
//
//    public void displayLandmarkAmenities(String size) {
//        if (size.equals("small")) {
//
//        } else if (size.equals("large")) {
//
//        }
//    }
//

}
