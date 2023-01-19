package com.appstacks.indiannaukribazaar.games;

public class GameItems {

    private String gameBanner;
    private String gameUrl;

    public GameItems(String gameBanner, String gameUrl) {
        this.gameBanner = gameBanner;
        this.gameUrl = gameUrl;
    }

    public GameItems() {
    }

    public String getGameBanner() {
        return gameBanner;
    }

    public void setGameBanner(String gameBanner) {
        this.gameBanner = gameBanner;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
