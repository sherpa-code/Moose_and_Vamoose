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

    private double extractedHunger;
    private double extractedThirst;
    private double extractedFuel;
    private double extractedFatigue;
    private double extractedSpeed = 0;
    private double extractedDistanceTraveled;
    private int extractedCash;
    private int extractedLastLandmarkIndex;

    // Moh: This variable gets updated by "Load Game" button and stores a
    // line of data which contains all saved stats of the palyer (last saved stats)
    //TODO: Play around with string loaded from text file to run a player with previously saved values

    public String loadedStr = ""; // Gets updated from "loadGameBtnClicked" method (when "Load Game" gets clicked)

    @FXML void startNewGameClicked(ActionEvent event) throws ParseException {
        System.out.println("startNewGameClicked()");
        PlayerStats player = new PlayerStats(
                0, 0, 100, 0,0,
                0, 500, 0
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
        System.out.println("loadGameBtnClicked()");
        PlayerStats player = new PlayerStats(
                extractedHunger, extractedThirst, extractedFuel, extractedFatigue,
                0, extractedDistanceTraveled, extractedCash, extractedLastLandmarkIndex
        );
        // TODO: REPLACE THIS WITH VALUES EXTRACTED FROM LOADED STRING
//        PlayerStats player = new PlayerStats(
//                5, 10, 100, 20,0,
//                1.95, 456
//        );


        try {
            File myObj = new File("SavedData.txt");
            Scanner readerObj = new Scanner(myObj);
            while (readerObj.hasNextLine()) {
                String data = readerObj.nextLine();
                //System.out.println(data);
                loadedStr = data; // loaderStr gets all stored data as a one line string
            }
            extractLoadedValues();
            readerObj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            Stage currentStage = (Stage) startNewGameButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gameplay.fxml"));
            Parent root = (Parent) loader.load();
            GameplayController gameplayController = loader.getController();
            gameplayController.storePlayer(player);
            System.out.println(gameplayController.player.getLastLandmarkIndex());
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

        //System.out.println(loadedStr);


    }


    @FXML void quitBtnClicked(ActionEvent event) {
        Platform.exit();

    }

    public void extractLoadedValues() {
        System.out.println("extractAndSaveLoadedValues() from \n      "+ loadedStr);




        // add value extraction functionality here
        extractedHunger = parseHunger(loadedStr);
        extractedThirst = parseThirst(loadedStr);
        extractedFuel = parseFuel(loadedStr);
        extractedFatigue = parseFatigue(loadedStr);
        extractedSpeed = 0;
        extractedDistanceTraveled = parseDistanceTraveled(loadedStr);
        extractedCash = parseCash(loadedStr);
        extractedLastLandmarkIndex = parseLastLandmarkIndex(loadedStr);



        System.out.println("debug extractedHunger = "+extractedHunger);
        System.out.println("debug extractedThirst = "+extractedThirst);
        System.out.println("debug extractedFuel = "+extractedFuel);
        System.out.println("debug extractedFatigue = "+extractedFatigue);
        System.out.println("debug extractedSpeed = "+extractedSpeed+" but this is always 0 upon Load");
        System.out.println("debug extractedDistanceTraveled = "+extractedDistanceTraveled);
        System.out.println("debug extractedCash = "+extractedCash);
        System.out.println("debug lastLandmarkIndex = "+extractedLastLandmarkIndex);


    }




    public static double parseHunger(String str){
        String resultStr = "";
        int startPoint = str.indexOf("hunger") + "hunger".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }

    public static double parseThirst(String str){
        String resultStr = "";
        int startPoint = str.indexOf("thirst") + "thirst".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }


    public static double parseFuel(String str){
        String resultStr = "";
        int startPoint = str.indexOf("fuel") + "fuel".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }


//    public static double restroomLoader(String str){
//        String resultStr = "";
//        int startPoint = str.indexOf("restroom") + "restroom".length()+1;
//        for (int i = startPoint; i <str.length() ; i++) {
//            if(str.charAt(i) != '|'){
//                resultStr += str.charAt(i);
//            }
//            else {break;}
//        }
//        return Double.parseDouble(resultStr);
//    }

    public static double parseFatigue(String str){
        String resultStr = "";
        int startPoint = str.indexOf("fatigue") + "fatigue".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);

    }

    public static double parseDistanceTraveled(String str){
        String resultStr = "";
        int startPoint = str.indexOf("distanceTraveled") + "distanceTraveled".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);

    }

    public static int parseCash(String str){
        String resultStr = "";
        int startPoint = str.indexOf("cash") + "cash".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Integer.parseInt(resultStr);

    }

    public static int parseLastLandmarkIndex(String str) {
        String resultStr = "";
        int startPoint = str.indexOf("lastLandmarkIndex") + "lastLandmarkIndex".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Integer.parseInt(resultStr);
    }
}
