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

public class MainMenuController {

    @FXML
    private Button startNewGameButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button quitButton;

    @FXML
    void loadGameBtnClicked(ActionEvent event) {
        //TODO:  loading process should be added after creation of saving functions

    }

    @FXML
    void optionsBtnClikced(ActionEvent event) {
        //TODO: to be removed !

        
    }

    @FXML
    void quitBtnClicked(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void startNewGameClicked(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1);
            stage.setTitle("Untitled Driver Game"); // displayed in window's title bar
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            System.out.println("Can't Load New Game!");
        };

    }

}