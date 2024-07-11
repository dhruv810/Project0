package Service;

import DAO.PlayerDAO;
import DAO.VideoGameDAO;
import Model.VideoGames;

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

    public boolean deleteVideoGame(int videoGameId, int playerId) {
        if (videoGameId < 0 || playerId < 0)
            return false;
        return videoGameDAO.deleteVideoGame(videoGameId, playerId);
    }

    public boolean updateOwner(int videoGameId, int playerId) {
        if (videoGameId < 0 || playerId < 0)
            return false;
        return videoGameDAO.updateVideoGame(videoGameId, playerId);
    }

    public ArrayList<VideoGames> getVideogamesByOwner(int playerId) {
        return videoGameDAO.getVideoGamesByOwner(playerId);
    }
}
