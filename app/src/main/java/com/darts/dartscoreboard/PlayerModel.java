package com.darts.dartscoreboard;

public class PlayerModel {
    private int playerId;
    private String playerName;
    private int checkNumber;


    //Constructor
    public PlayerModel(int playerId, String playerName, int checkNumber) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.checkNumber = checkNumber;

    }
    //Constructor empty
    public PlayerModel() {
    }

    //toString Method
    @Override
    public String toString() {
        return "com.darts.dartscoreboard.PlayerModel{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", checkNumber=" + checkNumber +
                +'}';
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }

}
