package sample;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.StringTokenizer;

public class Controller {

    @FXML
    private Button exitButton;
    @FXML
    private VBox startWindow, optionsWindow;
    @FXML
    private GridPane gameWindow;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void loadSecond() throws IOException {
        optionsWindow = FXMLLoader.load(getClass().getResource("options.fxml"));
        startWindow.getChildren().setAll(optionsWindow);
    }

    @FXML
    private void loadStart() throws IOException {
        startWindow = FXMLLoader.load(getClass().getResource("sample.fxml"));
        optionsWindow.getChildren().setAll(startWindow);
    }

    @FXML
    private void reloadStart() throws IOException{
        startWindow = FXMLLoader.load(getClass().getResource("sample.fxml"));
        gameWindow.getChildren().setAll(startWindow);
    }

    @FXML
    public void loadGame() throws IOException {
        Checkers checkers = new Checkers(60, 8);
        gameWindow = FXMLLoader.load(getClass().getResource("game.fxml"));
        gameWindow.getChildren().add(checkers.createContent());
        startWindow.getChildren().setAll(gameWindow);
    }
}

