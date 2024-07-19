package com.reveture.Service;

import com.reveture.DAO.PlayerDAO;
import com.reveture.DAO.VideoGameDAO;
import com.reveture.Exception.CustomException;
import com.reveture.Model.VideoGames;

import java.util.ArrayList;

public class VideoGameService {

    private final VideoGameDAO videoGameDAO;
    private final PlayerDAO playerDao;

    public VideoGameService() {
        this.videoGameDAO = new VideoGameDAO();
        this.playerDao = new PlayerDAO();
    }

    public ArrayList<VideoGames> getAllVideoGames() throws CustomException {
        return videoGameDAO.getAllVideoGames();
    }

    public VideoGames getVideoGameById(int gameId) throws CustomException {
        return videoGameDAO.getVideoGameById(gameId);
    }

    public VideoGames createVideoGame(VideoGames videogame) throws CustomException {
        if (videogame == null || videogame.getGameName().isEmpty() || videogame.getPrice() < 0) {
            throw new CustomException("Invalid game parameters");
        }
        if (videoGameDAO.checkVideoGameByNameAndOwner(videogame.getGameName(), videogame.getOwner())) {
            throw new CustomException("Player already owns this game");
        }
        if (playerDao.checkPlayerByPlayerId(videogame.getOwner())) {
            throw new CustomException("Make sure owner is real player");
        }
        return videoGameDAO.createVideoGame(videogame);

    }

    public boolean deleteVideoGame(int videoGameId) throws CustomException {
        if (videoGameId < 0) {
            throw new CustomException("GameId must be > 0");
        }
        else if (videoGameDAO.getVideoGameById(videoGameId) == null) {
            throw new CustomException("Video game with id: " + videoGameId + "  never existed");
        }
        return videoGameDAO.deleteVideoGame(videoGameId);
    }

    public boolean updateOwner(int videoGameId, int newPlayer) throws CustomException{
        if (videoGameId < 0) {
            throw new CustomException("Game id cannot be < 0");
        }
        else if (videoGameDAO.getVideoGameById(videoGameId) == null) {
            throw new CustomException("Video game with id: " + videoGameId + " not found");
        }
        else if (playerDao.checkPlayerByPlayerId(newPlayer)) {
            throw new CustomException("There is no player with Player id: " + newPlayer);
        }

        return videoGameDAO.updateVideoGame(videoGameId, newPlayer);
    }

    public ArrayList<VideoGames> getVideogamesByOwner(int playerId) throws CustomException {
        if (playerDao.checkPlayerByPlayerId(playerId)) {
            throw new CustomException("There is no player with player id: " + playerId);
        }
        return videoGameDAO.getVideoGamesByOwner(playerId);
    }
}
