package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Clock extends Pane {

    private Timeline animation;
    private int time = 10;
    private String S = "";
    private int Xvalue;
    private int Yvalue;

    Label label = new Label(Integer.toString(time));

    Clock( int x, int y){

        //time = tmp;
        Xvalue = x;
        Yvalue = y;

        //Label label = new Label(Integer.toString(time));

        label.setTranslateX(Xvalue);
        label.setTranslateY(Yvalue);

        getChildren().add(label);
        animation = new Timeline(new KeyFrame(Duration.seconds(1), e->timeLabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void timeLabel(){

        if(time > 0){
            time--;
            S = time + "";
            label.setText(S);
        }
        if (time == 0){
            S = "GAME OVER";
            label.setText(S);
        }
    }

}