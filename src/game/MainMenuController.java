package game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class MainMenuController {

    @FXML private Button startNewGameButton;
    @FXML private Button loadGameButton;
    @FXML private Button optionsButton;
    @FXML private Button quitButton;

    // Moh: This variable gets updated by "Load Game" button and stores a
    // line of data which contains all saved stats of the palyer (last saved stats)
    //TODO: Play around with string loaded from text file to run a player with previously saved values

    public String loadedStr = ""; // Gets updated from "loadGameBtnClicked" method (when "Load Game" gets clicked)

    @FXML void startNewGameClicked(ActionEvent event) throws ParseException {
        System.out.println("startNewGameClicked()");
        // debug new game distanceTraveled value
        PlayerStats player = new PlayerStats(
                0, 0, 100, 0,0,
                0, 500
        );
        // TODO: set it back to this for default values of this function
//        PlayerStats player = new PlayerStats(
//                0, 0, 100, 0,0,
//                0, 500
//        );
        try {
            Stage currentStage = (Stage) startNewGameButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gameplay.fxml"));
            Parent root = (Parent) loader.load();
            GameplayController gameplayController = loader.getController();
            gameplayController.storePlayer(player);
            gameplayController.updatePlayerStatsLabels(player);
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Moose and Vamoose - LOOK OUT!"); // displayed in window's title bar
            stage.setScene(scene);

            stage.show();
            currentStage.close();

        } catch(Exception e){
            System.out.println("Can't Start New Game!");
        }
    }

//    @FXML void startNewGameClicked(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
//            Parent root1 = fxmlLoader.load();
//            Stage stage = new Stage();
//            Scene scene = new Scene(root1);
//            stage.setTitle("Moose and Vamoose - LOOK OUT!"); // displayed in window's title bar
//            stage.setScene(scene);
//            stage.show();
//            Stage currentStage = (Stage) startNewGameButton.getScene().getWindow();
//            currentStage.close();
//
//        } catch(Exception e){
//            System.out.println("Can't Start New Game!");
//        }
//    }

    @FXML void loadGameBtnClicked(ActionEvent event) throws ParseException {
        System.out.println("startNewGameClicked()");
        // TODO: REPLACE THIS WITH VALUES EXTRACTED FROM LOADED STRING
        PlayerStats player = new PlayerStats(
                5, 10, 100, 20,0,
                1.95, 456
        );
        try {
            Stage currentStage = (Stage) startNewGameButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gameplay.fxml"));
            Parent root = (Parent) loader.load();
            GameplayController gameplayController = loader.getController();
            gameplayController.storePlayer(player);
            gameplayController.updatePlayerStatsLabels(player);
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Moose and Vamoose - LOOK OUT!"); // displayed in window's title bar
            stage.setScene(scene);

            stage.show();
            currentStage.close();

        } catch(Exception e){
            System.out.println("Can't Start New Game!");
        }


        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Landmark.fxml"));
        //TODO:  loading process should be added after creation of saving functions
        //loadGameplayerControllerAndCheck();
        // MOH: Reader object to load data
        try {
            File myObj = new File("SavedData.txt");
            Scanner readerObj = new Scanner(myObj);
            while (readerObj.hasNextLine()) {
                String data = readerObj.nextLine();
                //System.out.println(data);
                loadedStr = data; // loaderStr gets all stored data as a one line string

            }
            readerObj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(loadedStr);
        System.out.println("loadGameButtonClicked"+loadedStr);

    }


    @FXML void quitBtnClicked(ActionEvent event) {
        Platform.exit();

    }


}
