package com.example.a2048;

public class Score {
    private String namePlayer;
    private String score;


    public Score(String namePlayer, String score) {
        this.namePlayer = namePlayer;
        this.score = score;

    }

    public void formatScore() {
        int seconds = Integer.parseInt(this.score);

        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        this.score = String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public String getNamePlayer() {
        return this.namePlayer;
    }

    public String getScore() {
        return this.score;
    }


}
