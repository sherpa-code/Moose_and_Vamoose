package game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

import javax.sound.midi.*;
import java.io.File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
/**
 * Contains the functionality accessible through the Main Menu scene.
 */
public class MainMenuController {
    @FXML private Button startNewGameButton;
    private double extractedHunger;
    private double extractedThirst;
    private double extractedFuel;
    private double extractedFatigue;
    private double extractedSpeed = 0;
    private double extractedDistanceTraveled;
    private int extractedCash;
    private int extractedLastLandmarkIndex;
    public String loadedStr = ""; // Gets updated from "loadGameBtnClicked" method (when "Load Game" gets clicked)

    public void initialize() throws Exception {
        // disabled midi music // TODO: resolve how to control volume, currently plays at full volume
//        // Obtains the default Sequencer connected to a default device.
//        Sequencer sequencer = MidiSystem.getSequencer();
//
//        sequencer.open(); // Opens device, it should now acquire any system resources it requires and launch.
//
//        // create a stream from a file
//        InputStream is = new BufferedInputStream(new FileInputStream(new File("src\\game\\midi\\odenfld.mid")));
//
//        sequencer.setSequence(is); // Sets the current sequence for sequencer. The stream must point to MIDI file data.
//        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
//
//        sequencer.start(); // Starts playback of the MIDI data in the currently loaded sequence.
    }


    /**
     * Handles functionality of clicking Start New Game on main menu
     * @throws ParseException
     */
    @FXML void startNewGameClicked() throws ParseException {
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

    /**
     * Handles functionality of clicking Load Game on main menu
     * @throws ParseException
     */
    @FXML void loadGameBtnClicked() throws ParseException {
        System.out.println("loadGameBtnClicked()");
        try {
            File myObj = new File("SavedData.txt");
            Scanner readerObj = new Scanner(myObj);
            while (readerObj.hasNextLine()) {
                String data = readerObj.nextLine();
                loadedStr = data; // loaderStr gets all stored data as a one line string
            }
            extractLoadedValues();
            readerObj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        PlayerStats player = new PlayerStats(
                extractedHunger, extractedThirst, extractedFuel, extractedFatigue,
                0, extractedDistanceTraveled, extractedCash, extractedLastLandmarkIndex
        );

        try {
            Stage currentStage = (Stage) startNewGameButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Landmark.fxml"));
            Parent root = loader.load();
            LandmarkController landmarkController  = loader.getController();
            landmarkController.storePlayer(player);
            landmarkController.updateLandmarkStatsLabels(player);
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

    /**
     * Handles functionality of clicking Quit on main menu
     */
    @FXML void quitBtnClicked() {
        Platform.exit();

    }

    /**
     * Fire and store all saved data parsing functions
     */
    public void extractLoadedValues() {
        extractedHunger = parseHunger(loadedStr);
        extractedThirst = parseThirst(loadedStr);
        extractedFuel = parseFuel(loadedStr);
        extractedFatigue = parseFatigue(loadedStr);
        extractedSpeed = 0;
        extractedDistanceTraveled = parseDistanceTraveled(loadedStr);
        extractedCash = parseCash(loadedStr);
        extractedLastLandmarkIndex = parseLastLandmarkIndex(loadedStr);
    }


    /**
     * extract hunger from input string
     * @param str load data string
     * @return
     */
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

    /**
     * extract thirst from input string
     * @param str load data string
     * @return
     */
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

    /**
     * extract fuel from input string
     * @param str load data string
     * @return
     */
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

    /**
     * extract fatigue from input string
     * @param str load data string
     * @return
     */
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

    /**
     * extract distance traveled from input string
     * @param str load data string
     * @return
     */
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

    /**
     * extract cash from input string
     * @param str load data string
     * @return
     */
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

    /**
     * extract last landmark index from input string
     * @param str load data string
     * @return
     */
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
