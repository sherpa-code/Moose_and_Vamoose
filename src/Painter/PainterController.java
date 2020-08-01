// Controller for the Painter app
package Painter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PainterController {
   private enum PenSize { // enum representing pen sizes
      SMALL(2), 
      MEDIUM(4), 
      LARGE(6);
      
      private final int radius;
      
      PenSize(int radius) {this.radius = radius;}
      
      public int getRadius() {return radius;}
   };

   // instance variables that refer to GUI components
   @FXML private RadioButton blackRadioButton;
   @FXML private RadioButton redRadioButton;
   @FXML private RadioButton greenRadioButton;
   @FXML private RadioButton blueRadioButton;
   @FXML private RadioButton smallRadioButton;
   @FXML private RadioButton mediumRadioButton;
   @FXML private RadioButton largeRadioButton;
   @FXML private Pane drawingAreaPane;
   @FXML private ToggleGroup colorToggleGroup;
   @FXML private ToggleGroup sizeToggleGroup;
   @FXML private TextField redTextField;
   @FXML private TextField greenTextField;
   @FXML private TextField blueTextField;
   @FXML private TextField alphaTextField;

   // instance variables for managing Painter state
   private PenSize radius = PenSize.MEDIUM; // radius of circle
   private Paint brushColor = Color.BLACK; // drawing color
   private int red = 0;
   private int green = 0;
   private int blue = 0;
   private double alpha = 1.0;
   
   // set user data for the RadioButtons
   public void initialize() {
      // user data on a control can be any Object
      blackRadioButton.setUserData(Color.BLACK);
      redRadioButton.setUserData(Color.RED);
      greenRadioButton.setUserData(Color.GREEN);
      blueRadioButton.setUserData(Color.BLUE);
      smallRadioButton.setUserData(PenSize.SMALL);
      mediumRadioButton.setUserData(PenSize.MEDIUM);
      largeRadioButton.setUserData(PenSize.LARGE);
      updateColorFields();
   }
   
   // handles drawingArea's onMouseDragged MouseEvent
   @FXML
   private void drawingAreaMouseDragged(MouseEvent e) {
      Circle newCircle = new Circle(e.getX(), e.getY(), radius.getRadius(), brushColor);
      drawingAreaPane.getChildren().add(newCircle); 
   }
   
   // handles color RadioButton's ActionEvents
   @FXML
   private void colorRadioButtonSelected(ActionEvent e) {
      brushColor = (Paint) colorToggleGroup.getSelectedToggle().getUserData();
//      // user data for each color RadioButton is the corresponding Color
//      brushColor =  colorToggleGroup.getSelectedToggle().getUserData();
//      Color redColor = brushColor.getRed();
//      //redTextField = String.valueOf(brushColor.getRed());
//      //      redTextField = String.valueOf(brushColor.getRed()*255);

      if (brushColor.equals(Color.RED)) {
         saveColorValues(255,0,0,1);
      } else if (brushColor.equals(Color.GREEN)) {
         saveColorValues(0,255,0,1);
      } else if (brushColor.equals(Color.BLUE)) {
         saveColorValues(0,0,255,1);
      } else if (brushColor.equals(Color.BLACK)) {
         saveColorValues(0,0,0,1);
      }
   } 
      
   // handles size RadioButton's ActionEvents
   @FXML
   private void sizeRadioButtonSelected(ActionEvent e) {
      // user data for each size RadioButton is the corresponding PenSize
      radius = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
   }


   // handles Choose Color Button's Clicked Event
   @FXML
   private void chooseColorButtonPressed(ActionEvent event) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource(
                 "ColorChooser.fxml"));
         Parent root = loader.load();
         ColorChooserController ctrl = loader.getController();
         ctrl.setColorSliders(this.red,this.green,this.blue,this.alpha, this);

         Scene newScene = new Scene(root);
         Stage newStage = new Stage();
         newStage.setScene(newScene);
         newStage.show();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // handles Undo Button's ActionEvents
   @FXML
   private void undoButtonPressed(ActionEvent event) {
      int count = drawingAreaPane.getChildren().size();

      // if there are any shapes remove the last one added
      if (count > 0) {
         drawingAreaPane.getChildren().remove(count - 1);
      }
   }
   
   // handles Clear Button's ActionEvents
   @FXML
   private void clearButtonPressed(ActionEvent event) {
      drawingAreaPane.getChildren().clear(); // clear the canvas
   }

   // Stores the Chosen Color in the main scene
   @FXML
   public void saveColorValues(Integer red, Integer green, Integer blue, double alpha) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      updateColorFields();
   }

   // handles Choose Color Button's Clicked Event
   @FXML
   private void updateColorFields() {
      redTextField.setText(String.valueOf(this.red));
      greenTextField.setText(String.valueOf(this.green));
      blueTextField.setText(String.valueOf(this.blue));
      alphaTextField.setText(String.valueOf(this.alpha));
      brushColor = Color.rgb(this.red, this.green, this.blue, this.alpha);
   }
}