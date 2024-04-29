package view;

import controller.StaticPieces;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class CountDown {
    private int minute, second;
    private final String name;
    private final Timer timer;
    private Boolean pause;
    private Boolean fullTime;
    public CountDown(String name){
        this.pause = true;
        this.name = name;
        this.fullTime = false;
        this.timer = new Timer();
    }
    public void start(JLabel label){
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTime(label);
            }
        }, 0, 1000);
    }
    public void setTime(int minute, int second){
        this.minute = minute;
        this.second = second; this.pause = true;
        this.fullTime = false;
    }

    private void updateTime(JLabel label){
        if(!pause && !fullTime&& !StaticPieces.getSetting().getStatus()){
            if (this.minute == 0 && this.second == 0) {
                this.fullTime = true;
                JOptionPane.showMessageDialog(null, "Hết giờ\n" + name + " thua");
            } else {
                if (this.second == 0) {
                    this.minute--;
                    this.second = 59;
                } else {
                    this.second--;
                }
                String temp1 = (this.minute < 10) ? "0" : "";
                String temp2 = (this.second < 10) ? "0" : "";
                label.setText(temp1 + this.minute + " : " + temp2 + this.second);
            }
        }
    }
    public Boolean getFullTime() { return fullTime;}
    public void stop(){ this.pause = true;}
    public void resume(){ this.pause = false;}
    public int getMinute() {return minute;}
    public int getSecond() {return second;}
}
