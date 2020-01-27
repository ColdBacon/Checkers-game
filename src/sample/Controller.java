package sample;

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

    @FXML private Button exitButton;
    @FXML private CheckBox singlePlayerButton, multiPlayerButton;
    @FXML private VBox startWindow, optionsWindow;
    @FXML private GridPane gameWindow;
    @FXML private ComboBox rozmiarCombo, colorCombo;
    @FXML private Slider clock;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage)exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /*
    @FXML
    private void loadGame(){
        Checkers checkers = new Checkers();
        startWindow.getChildren().setAll(checkers.createContent());
    }

     */
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
    private void reloadStart() throws IOException {
        startWindow = FXMLLoader.load(getClass().getResource("sample.fxml"));
        gameWindow.getChildren().setAll(startWindow);
    }

    @FXML
    private void handleSingleplayerBox(){
        if(singlePlayerButton.isSelected()){
            multiPlayerButton.setSelected(false);
        }
    }

    @FXML
    private void handleMultiplayerBox(){
        if(multiPlayerButton.isSelected()){
            singlePlayerButton.setSelected(false);
        }
    }

    @FXML
    public void loadGame () throws IOException {
        Checkers checkers = new Checkers(60,8);
        gameWindow = FXMLLoader.load(getClass().getResource("game.fxml"));
        gameWindow.getChildren().add(checkers.createContent());
        startWindow.getChildren().setAll(gameWindow);
    }

    @FXML
    public void board_size(){
        String size = rozmiarCombo.getSelectionModel().getSelectedItem().toString();
        int board_size = Integer.parseInt(String.valueOf(size.charAt(0)));
        if (board_size == 1) board_size = 10;
        System.out.println(board_size);
    }

    @FXML
    public void colors(){
        String colors =  colorCombo.getSelectionModel().getSelectedItem().toString();

        // Tworzymy obiekt StringTokenizer
        StringTokenizer stringTokenizer = new StringTokenizer(colors, " x ");
        while(stringTokenizer.hasMoreTokens())
        {
            String color1 = stringTokenizer.nextToken();
            String color2 = stringTokenizer.nextToken();
            System.out.println(color1);
            System.out.println(color2);
        }
    }


    @FXML
    public void timer(){
        String time = Double.toString(clock.getValue());
        System.out.println(time);
    }
}

