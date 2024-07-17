package com.reveture.Service;

import com.reveture.DAO.PlayerDAO;
import com.reveture.DAO.VideoGameDAO;
import com.reveture.Model.VideoGames;

import java.util.ArrayList;

public class VideoGameService {

    private final VideoGameDAO videoGameDAO;
    private final PlayerDAO playerDao;

    public VideoGameService() {
        this.videoGameDAO = new VideoGameDAO();
        this.playerDao = new PlayerDAO();
    }

    public ArrayList<VideoGames> getAllVideoGames() {
        return videoGameDAO.getAllVideoGames();
    }

    public VideoGames getVideoGameById(int gameId) {
        return videoGameDAO.getVideoGameById(gameId);
    }

    public VideoGames createVideoGame(VideoGames videogame) {
        if (videogame == null || videogame.getGameName().isEmpty() || videogame.getPrice() < 0) {
            System.out.println("Invalid game parameters");
            return null;
        }
        if (videoGameDAO.checkVideoGameByNameAndOwner(videogame.getGameName(), videogame.getOwner())) {
            System.out.println("game already exists failed");
            return null;
        }
        if (! playerDao.checkPlayerByPlayerId(videogame.getOwner())) {
            System.out.println("player not found");
            return null;
        }
        return videoGameDAO.createVideoGame(videogame);

    }

    public boolean deleteVideoGame(int videoGameId) {
        if (videoGameId < 0)
            return false;
        return videoGameDAO.deleteVideoGame(videoGameId);
    }

    public boolean updateOwner(int videoGameId, int newPlayer) {
        if (videoGameId < 0 || ! playerDao.checkPlayerByPlayerId(newPlayer))
            return false;
        return videoGameDAO.updateVideoGame(videoGameId, newPlayer);
    }

    public ArrayList<VideoGames> getVideogamesByOwner(int playerId) {
        return videoGameDAO.getVideoGamesByOwner(playerId);
    }
}
