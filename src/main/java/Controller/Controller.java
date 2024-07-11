package Controller;

import Model.Player;
import Model.VideoGames;
import Service.PlayerService;
import Service.VideoGameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class Controller {

    private final PlayerService playerService;
    private final VideoGameService videoGameService;

    public Controller() {
        this.playerService = new PlayerService();
        this.videoGameService = new VideoGameService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("createplayer", this::createNewPlayer);
        app.post("login", this::login);
        app.get("videogames", this::getAllVideogames);
        app.get("videogames/{gameId}", this::getVideogameById);
        app.post("createvideogame", this::createVideoGame);
        app.delete("deletegame/{gameId}/{playerId}", this::deleteGame);
        app.patch("updateowner/{gameId}/{playerId}", this::updateOwner);
        app.get("videogamesbyowner/{playerId}", this::getVideogamesByOwner);
        app.get("/", ctx -> ctx.result("Hello, Javalin!"));
        return app;
    }

    public void createNewPlayer(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Player player = mapper.readValue(context.body(), Player.class);

        Player newlyCreatedPlayer = playerService.createNewPlayer(player);

        if (newlyCreatedPlayer == null) {
            context.status(400);
            context.result("Player could not be created");
        }
        else {
            context.json(mapper.writeValueAsString(newlyCreatedPlayer));
        }

    }

    public void login(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Player player = mapper.readValue(context.body(), Player.class);

        Player varifiedPlayer = playerService.verifyPlayerCredentials(player);
        if (varifiedPlayer == null) {
            context.status(401);
        }
        else {
            context.json(mapper.writeValueAsString(varifiedPlayer));
        }
    }

    private void getAllVideogames(Context context) {
        ArrayList<VideoGames> videogames = videoGameService.getAllVideoGames();
        context.json(videogames);
    }

    private void getVideogameById(Context context) throws JsonProcessingException {
        int gameId = Integer.parseInt(context.pathParam("gameId"));
        ObjectMapper mapper = new ObjectMapper();

        VideoGames videogames = videoGameService.getVideoGameById(gameId);

        if (videogames == null) {
            context.status(400);
            context.result("Game not found");
        }
        else {
            context.json(mapper.writeValueAsString(videogames));
        }
    }

    private void createVideoGame(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        VideoGames videogame = mapper.readValue(context.body(), VideoGames.class);

        VideoGames createdVideogame = videoGameService.createVideoGame(videogame);

        if (createdVideogame == null) {
            context.status(402);
            context.result("Videogame creation failed");
        }
        else {
            context.json(mapper.writeValueAsString(createdVideogame));
        }
    }

    private void deleteGame(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        int videoGameId = Integer.parseInt(context.pathParam("gameId"));
        int playerId = Integer.parseInt(context.pathParam("playerId"));

        boolean deleted = videoGameService.deleteVideoGame(videoGameId, playerId);

        if (deleted) {
            context.result("Videogame successfully deleted.");
        }
        else {
            context.status(403);
            context.result("Could not delete videogame.");
        }
    }

    private void updateOwner(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        int videoGameId = Integer.parseInt(context.pathParam("gameId"));
        int playerId = Integer.parseInt(context.pathParam("playerId"));

        boolean updated = videoGameService.updateOwner(videoGameId, playerId);

        if (updated) {
            context.result("Videogame owner successfully updated.");
        }
        else {
            context.status(404);
            context.result("Could not update videogame owner.");
        }
    }

    private void getVideogamesByOwner(Context context) {
        int playerId = Integer.parseInt(context.pathParam("playerId"));

        ArrayList<VideoGames> videoGames = videoGameService.getVideogamesByOwner(playerId);
        context.json(videoGames);
    }

}
