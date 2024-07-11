package Model;

import java.util.Objects;

public class VideoGames {
    int gameId;
    String gameName;
    double price;
    int owner;

    public VideoGames() {
    }

    public VideoGames(String gameName, double price, int owner) {
        this.gameName = gameName;
        this.price = price;
        this.owner = owner;
    }


    public VideoGames(int gameId, String gameName, double price, int owner) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.price = price;
        this.owner = owner;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoGames that = (VideoGames) o;
        return gameId == that.gameId && Double.compare(price, that.price) == 0 && Objects.equals(gameName, that.gameName) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, gameName, price, owner);
    }

    @Override
    public String toString() {
        return "VideoGames{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                ", owner=" + owner +
                '}';
    }
}
