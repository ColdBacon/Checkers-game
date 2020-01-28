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
    private CheckBox singlePlayerButton, multiPlayerButton;
    @FXML
    private VBox startWindow, optionsWindow;
    @FXML
    private GridPane gameWindow;
    @FXML
    private ComboBox rozmiarCombo, colorCombo;
    @FXML
    private Slider clock;

    Options options = new Options("WHITE", "RED",8,1,"SinglePlayer" );

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
    private void handleSingleplayerBox() {
        if (singlePlayerButton.isSelected()) {
            multiPlayerButton.setSelected(false);
        }
        //System.out.println("SinglePlayer");
        options.setGameMode("SinglePlayer");
    }

    @FXML
    private void handleMultiplayerBox() {
        if (multiPlayerButton.isSelected()) {
            singlePlayerButton.setSelected(false);
        }
        //System.out.println("MultiPlayer");
        options.setGameMode("MultiPlayer");
    }

    @FXML
    public void loadGame() throws IOException {
        Checkers checkers = new Checkers(50, 10);
        gameWindow = FXMLLoader.load(getClass().getResource("game.fxml"));
        gameWindow.getChildren().add(checkers.createContent());
        startWindow.getChildren().setAll(gameWindow);
    }

    @FXML
    public void board_size() {
        String size = rozmiarCombo.getSelectionModel().getSelectedItem().toString();
        int board_size = Integer.parseInt(String.valueOf(size.charAt(0)));
        if (board_size == 1) board_size = 10;
        //System.out.println(board_size);
        options.setSize(board_size);
    }

    @FXML
    public void colors() {
        String colors = colorCombo.getSelectionModel().getSelectedItem().toString();

        // Tworzymy obiekt StringTokenizer
        StringTokenizer stringTokenizer = new StringTokenizer(colors, " x ");
        while (stringTokenizer.hasMoreTokens()) {
            String color1 = stringTokenizer.nextToken();
            String color2 = stringTokenizer.nextToken();
            //System.out.println(color1);
            //System.out.println(color2);
            options.setColor1(color1);
            options.setColor2(color2);
        }
    }

    @FXML
    public void timer() {
        int time = (int)clock.getValue();
        //System.out.println(time);
        options.setTime(time);
    }
}

class Options{
    String color1;
    String color2;
    int size;
    int time;
    String gameMode;

    public Options(String c1, String c2, int s, int t, String g){
        this.color1 = c1;
        this.color2 = c2;
        this.size = s;
        this.time = t;
        this.gameMode = g;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSize() {
        return size;
    }
}

