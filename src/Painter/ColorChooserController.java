package Painter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColorChooserController {
    @FXML private Slider redSlider;
    @FXML private Slider greenSlider;
    @FXML private Slider blueSlider;
    @FXML private Slider alphaSlider;
    @FXML private TextField redTextField;
    @FXML private TextField greenTextField;
    @FXML private TextField blueTextField;
    @FXML private TextField alphaTextField;
    @FXML private Rectangle colorRectangle;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private PainterController painterController;



    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private double alpha = 1.0;

    public void initialize() {
        redTextField.textProperty().bind( redSlider.valueProperty().asString());
        greenTextField.textProperty().bind( greenSlider.valueProperty().asString());
        blueTextField.textProperty().bind( blueSlider.valueProperty().asString());
        alphaTextField.textProperty().bind( alphaSlider.valueProperty().asString());

        redSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    red = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
            }
        );

        greenSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    green = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
            }
        );

        blueSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    blue = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
            }
        );

        alphaSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    alpha = newValue.doubleValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
            }
        );
    }

    // handles Cancel Button's ActionEvents
    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // handles Save Button's ActionEvents
    @FXML
    private void saveButtonPressed(ActionEvent event) {
        painterController.saveColorValues(this.red, this.green, this.blue, this.alpha);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }


    // handles Save Button's ActionEvents
    @FXML
    public void setColorSliders(Integer red, Integer green, Integer blue, double alpha, PainterController painterController) {
        this.red = red;
        redSlider.setValue(this.red);
        this.green = green;
        greenSlider.setValue(this.green);
        this.blue = blue;
        blueSlider.setValue(this.blue);
        this.alpha = alpha;
        alphaSlider.setValue(this.alpha);
        this.painterController = painterController;
    }
}
