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

    @FXML void startNewGameClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1);
            stage.setTitle("Moose and Vamoose - LOOK OUT!"); // displayed in window's title bar
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) startNewGameButton.getScene().getWindow();
            currentStage.close();

        } catch(Exception e){
            System.out.println("Can't Start New Game!");
        }
    }

    @FXML void loadGameBtnClicked(ActionEvent event) {
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
