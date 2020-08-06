package game;

/**
Advanced J2SE Programming CP2560 - Term Project
Untitled Driver Game
@author joseph.crane
 @author paul.gatto
 @author mohammad.f.rad
 */

// Main application class that loads and displays the initial screen.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main extends Application {

    static ArrayList<String> landmarks = new ArrayList<>();


    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //String timeStamp = new SimpleDateFormat("MMMMM dd, yyyy").format(Calendar.getInstance().getTime());
        //Date timeStamp = new SimpleDateFormat("MMMMM dd, yyyy").parse("July 1, 2020");
        //System.out.println(timeStamp);
        //Parent root = FXMLLoader.load(getClass().getResource("TitleScreen.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Gameplay.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Untitled Driver Game"); // displayed in window's title bar
        stage.setScene(scene);
        stage.show();
    }

}