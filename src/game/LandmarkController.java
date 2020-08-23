package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class LandmarkController {
    @FXML    private Label hungerValueLabel;
    @FXML    private Label thirstValueLabel;
    @FXML    private Label fuelValueLabel;
    @FXML    private Label fatigueValueLabel;
    @FXML    private Label cashValueLabel;
    @FXML    private Label landmarkFuelCostLabel;
    @FXML    private Label advertisedFuelForLabel;
    @FXML    private Label advertisedFuelLabel;
    @FXML    private Label landmarkFuelQuantity;
    @FXML    private Label landmarkHotelCostLabel;
    @FXML    private Label advertisedFatigueForLabel;
    @FXML    private Label advertisedFatigueLabel;
    @FXML    private Label landmarkFatigueQuantity;
    @FXML    private Label landmarkFoodQuantity;
    @FXML    private Label landmarkThirstQuantity;
    @FXML    private Button buyFuelButton;
    @FXML    private Button buyDrinkButton;
    @FXML    private Button buyHungerButton;
    @FXML    private Button rentHotelButton;
    @FXML    private Button departLandmarkButton;
    @FXML    private ImageView hotelImageView;
    @FXML    private ImageView storeImageView;
    @FXML    private ImageView gasStationImageView;
    @FXML    private Label landmarkNameLabel;
    @FXML    private Label landmarkSizeLabel;
    @FXML    private Label landmarkInsufficientFundsLabel;
    @FXML    public ImageView landmarkBackgroundLargeImageView;
    @FXML    public ImageView landmarkBackgroundSmallImageView;
    private PlayerStats player;
    String [][] savingObj;

    /**
     * A method that updates player status labels to current running values, truncated (via casting) to an integer
     *
     * @param player as an instance of PlayerStats class and gets its properties
     */
    //TODO: Should updates stats of the player during a time period (per second?!)
    public void updateLandmarkStatsLabels(PlayerStats player) {
        hungerValueLabel.setText(String.valueOf((int) player.getHunger()));
        thirstValueLabel.setText(String.valueOf((int) player.getThirst()));
        fuelValueLabel.setText(String.valueOf((int) player.getFuel()));
        fatigueValueLabel.setText(String.valueOf((int) player.getFatigue()));
        cashValueLabel.setText(String.valueOf(player.getCash()));
        landmarkNameLabel.setText(player.getNextLandmarkName());
        landmarkSizeLabel.setText(player.getNextLandmarkSize());
    }

    /**
     * Checks the landmark size,
     * shows the building images corresponding to small or large size,
     * hides the others
     */
    private void setLandmarkVisibility(){
        String landmarkSize = player.landmarkAttributes[player.getLastLandmarkIndex()+1][2];
        if (landmarkSize == "large") {
            hotelImageView.setVisible(true);
            gasStationImageView.setVisible(true);
            storeImageView.setVisible(false);
            landmarkBackgroundLargeImageView.setVisible(true);
            landmarkBackgroundSmallImageView.setVisible(false);
        } else if(landmarkSize == "small"){
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

            landmarkBackgroundLargeImageView.setVisible(false);
            landmarkBackgroundSmallImageView.setVisible(true);
        }
        else {
            System.out.println("Something went wrong here for background image");
        }
    }

    /**
     * enables other controllers to pass the current player (PlayerStats object) to be stored
     * @param Player
     */
    public void storePlayer(PlayerStats Player) {
        player = Player;
        setLandmarkVisibility();
    }

    /**
     * SavingObj, a multidimensional string array, is stored locally
     * @param SavingObj
     */
    public void storeSavingObj(String[][] SavingObj) {
        savingObj = SavingObj;
    }

    /**
     * Attempts to save the player's current stats at the current landmark
     * @throws IOException
     */
    @FXML
    void saveGameAtLandmarkClicked() {
        try { // writes & Stores all current stats from "savingObj" to "SavedData.txt" file
            File myObj = new File("SavedData.txt"); // Text file created
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try { // contents of saveObj are being copied to the text file
            FileWriter writerObj = new FileWriter("SavedData.txt");
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

            for (int i = 0; i < savingObj.length; i++) {
                writerObj.write(savingObj[i][0] + "=" + savingObj[i][1]+"|");
            }
            writerObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

//    @FXML
//    void saveGameAtLandmarkClicked() throws IOException {
//        try { // writes & Stores all current stats from "savingObj" to "SavedData.txt" file
//            File myObj = new File("SavedData.txt"); // Text file created
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//        try { // contents of saveObj are being copied to the text file
//            FileWriter writerObj = new FileWriter("SavedData.txt");
//            String [][] savingObj = {
//                    {"hunger", String.valueOf(player.getHunger())},
//                    {"thirst", String.valueOf(player.getThirst())},
//                    {"fuel", String.valueOf(player.getFuel())},
////            {"restroom", String.valueOf(player.getRestroom())}, // restroom is removed
//                    {"fatigue", String.valueOf(player.getFatigue())},
//                    {"speed", String.valueOf(player.getSpeed())},
//                    {"distanceTraveled", String.valueOf(player.getDistanceTraveled())},
//                    {"cash", String.valueOf(player.getCash())},
//                    {"lastLandmarkIndex", String.valueOf(player.getLastLandmarkIndex())}
//            };
//
//            for (int i = 0; i < savingObj.length; i++) {
//                writerObj.write(savingObj[i][0] + "=" + savingObj[i][1]+"|");
//            }
//            writerObj.close();
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }

    /**
     * checks if the player has sufficient cash for the attribute restoration, then either:
     * 1) if player has enough cash:
     * subtracts the attribute restoration cost from cash,
     * restores the thirst by the restoration amount, and
     * sets the Insufficient Funds label to invisible
     * 2) if player does not have enough cash:
     * sets the Insufficient Funds label to visible,
     * hides the Buy button of this attribute
     */
    @FXML
    void buyDrinkAtLandmarkClicked() {
        if (player.getCash() < 3) {
            landmarkInsufficientFundsLabel.setVisible(true);
            buyDrinkButton.setVisible(false);

        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 3);

            if (player.getThirst() - Double.parseDouble(landmarkThirstQuantity.getText()) < 0) {
                player.setThirst(0.0);
            } else {
                player.setThirst(player.getThirst() - Double.parseDouble(landmarkThirstQuantity.getText()));
            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            thirstValueLabel.setText(String.valueOf((int) player.getThirst()));
        }

    }

    /**
     * checks if the player has sufficient cash for the attribute restoration, then either:
     * 1) if player has enough cash:
     * subtracts the attribute restoration cost from cash,
     * restores the thirst by the restoration amount, and
     * sets the Insufficient Funds label to invisible
     * 2) if player does not have enough cash:
     * sets the Insufficient Funds label to visible,
     * hides the Buy button of this attribute
     */
    @FXML
    void buyFoodAtLandmarkClicked() {
        if (player.getCash() < 12) {
            landmarkInsufficientFundsLabel.setVisible(true);
            buyHungerButton.setVisible(false);
        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 12);

            if (player.getHunger() - Double.parseDouble(landmarkFoodQuantity.getText()) < 0) {
                player.setHunger(0.0);
            }
            else {
                player.setHunger(player.getHunger() - Double.parseDouble(landmarkFoodQuantity.getText()));
            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            hungerValueLabel.setText(String.valueOf((int) player.getHunger()));
        }

    }

    /**
     * checks if the player has sufficient cash for the attribute restoration, then either:
     * 1) if player has enough cash:
     * subtracts the attribute restoration cost from cash,
     * restores the thirst by the restoration amount, and
     * sets the Insufficient Funds label to invisible
     * 2) if player does not have enough cash:
     * sets the Insufficient Funds label to visible,
     * hides the Buy button of this attribute
     */
    @FXML
    void buyFuelAtLandmarkClicked() {
        if (player.getCash() < 20) {
            landmarkInsufficientFundsLabel.setVisible(true);
            buyFuelButton.setVisible(false);
        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 20);

            if (player.getFuel() + Double.parseDouble(landmarkFuelQuantity.getText()) >= 100.00) {
                player.setFuel(100.0);
            }
            else {
                player.setFuel(player.getFuel() + Double.parseDouble(landmarkFuelQuantity.getText()));
            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            fuelValueLabel.setText(String.valueOf((int) player.getFuel()));
        }
    }

    /**
     * checks if the player has sufficient cash for the attribute restoration, then either:
     * 1) if player has enough cash:
     * subtracts the attribute restoration cost from cash,
     * restores the thirst by the restoration amount, and
     * sets the Insufficient Funds label to invisible
     * 2) if player does not have enough cash:
     * sets the Insufficient Funds label to visible,
     * hides the Buy button of this attribute
     */
    @FXML
    void rentHotelRoomAtLandmarkClicked() {
        if (player.getCash() < 125) {
            landmarkInsufficientFundsLabel.setVisible(true);
            rentHotelButton.setVisible(false);
        } else {
            landmarkInsufficientFundsLabel.setVisible(false);
            player.setCash(player.getCash() - 125);

            if (player.getFatigue() - Double.parseDouble(landmarkFatigueQuantity.getText()) <= 0.0) {
                player.setFatigue(0.0);
            }
            else {
                player.setFatigue(player.getFatigue() - Double.parseDouble(landmarkFatigueQuantity.getText()));
            }
            cashValueLabel.setText(String.valueOf(player.getCash()));
            fatigueValueLabel.setText(String.valueOf((int) player.getFatigue()));
        }

    }


    /**
     * returns the player to the driving gameplay loop, with the stats they left the landmark with
     * @throws IOException
     */
    @FXML
    void departCurrentLandmarkClicked() throws IOException {
        Stage currentStage = (Stage) departLandmarkButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gameplay.fxml"));
        //Parent root = (Parent) loader.load();
        Parent root = loader.load();
        GameplayController gameplayController = loader.getController();
        gameplayController.cancelTick();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("You are back on the road. Stay vigilant.");
        stage.setScene(scene);

        gameplayController.getPlayer().setFuel(player.getFuel());
        gameplayController.getPlayer().setHunger(player.getHunger());
        gameplayController.getPlayer().setThirst(player.getThirst());
        gameplayController.getPlayer().setFatigue(player.getFatigue());
        gameplayController.getPlayer().setCash(player.getCash());
        gameplayController.getPlayer().setLastLandmarkIndex(player.getLastLandmarkIndex()+1);
        gameplayController.getPlayer().setDistanceTraveled(player.getDistanceTraveled());
        gameplayController.updatePlayerStatsLabels(player);

        gameplayController.beginTick();
        stage.show();
        currentStage.close();
    }

    public PlayerStats getPlayer() {
        return player;
    }
}
