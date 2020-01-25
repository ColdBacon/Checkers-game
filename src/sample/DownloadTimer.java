package sample;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;

public class DownloadTimer{
    private int minutes;
    private int seconds;
    private Timer innerTimer = new Timer();
    private TimerTask innerTask;
    private boolean isActive;

    public DownloadTimer(int minutes, int seconds) {
        if (seconds > 60) {
            int minToAdd = seconds / 60;
            this.minutes = minutes;
            this.minutes += minToAdd;
            this.seconds = seconds % 60;}
        else {
            this.minutes = minutes;
            this.seconds = seconds;}
        }

    public void start() {
        innerTask = new TimerTask() {
            @Override
            public void run() {
                isActive = true;
                //System.out.println(getTime());
                if (seconds == 0 && minutes > 0){
                    minutes -= 1;
                    seconds = 59; }
                else if (seconds == 0 && minutes == 0){
                    isActive = false;
                    innerTimer.cancel();
                    innerTimer.purge();
                    System.out.println("DownloadTimer DONE"); }
                else {
                    seconds -= 1; }
             }
        };
        innerTimer.scheduleAtFixedRate(innerTask, 0, 1000);
    }
}