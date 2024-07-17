package com.reveture.Controller;

import com.reveture.Model.Player;
import com.reveture.Model.VideoGames;
import com.reveture.Service.PlayerService;
import com.reveture.Service.VideoGameService;
import io.javalin.Javalin;
import io.javalin.http.Context;


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
        app.delete("deletegame/{gameId}", this::deleteGame); // verify
        app.patch("updateowner/{gameId}", this::updateOwner); // verify
        app.get("videogamesbyowner/{playerId}", this::getVideogamesByOwner);
        app.get("/", ctx -> ctx.result("Hello, Javalin!"));
        return app;
    }

    public void createNewPlayer(Context context) {
        Player player = context.bodyAsClass(Player.class);

        Player newlyCreatedPlayer = playerService.createNewPlayer(player);

        if (newlyCreatedPlayer == null) {
            context.status(400);
            context.result("Player could not be created");
        }
        else {
            context.json(newlyCreatedPlayer);
        }

    }

    public void login(Context context) {
        Player player = context.bodyAsClass(Player.class);

        Player varifiedPlayer = playerService.verifyPlayerCredentials(player);
        if (varifiedPlayer == null) {
            context.status(401);
            context.result("invalid credentials");
        }
        else {
            context.json(varifiedPlayer);
        }
    }

    private void getAllVideogames(Context context) {
        ArrayList<VideoGames> videogames = videoGameService.getAllVideoGames();
        context.json(videogames);
    }

    private void getVideogameById(Context context) {
        int gameId = Integer.parseInt(context.pathParam("gameId"));

        VideoGames videogames = videoGameService.getVideoGameById(gameId);

        if (videogames == null) {
            context.status(400);
            context.result("Game not found");
        }
        else {
            context.json(videogames);
        }
    }

    private void createVideoGame(Context context) {
        VideoGames videogame = context.bodyAsClass(VideoGames.class);

        VideoGames createdVideogame = videoGameService.createVideoGame(videogame);

        if (createdVideogame == null) {
            context.status(402);
            context.result("Videogame creation failed");
        }
        else {
            context.json(createdVideogame);
        }
    }

    private void deleteGame(Context context) {
        int videoGameId = Integer.parseInt(context.pathParam("gameId"));

        boolean deleted = videoGameService.deleteVideoGame(videoGameId);

        if (deleted) {
            context.result("Videogame successfully deleted.");
        }
        else {
            context.status(403);
            context.result("Could not delete videogame.");
        }
    }

    private void updateOwner(Context context) {
        int videoGameId = Integer.parseInt(context.pathParam("gameId"));
        String newPlayerId = context.queryParam("newPlayerId");

        if (newPlayerId == null || newPlayerId.isEmpty()) {
            context.result("Provide newPlayerId in query parameter");
            return;
        }
        int newPlayer = Integer.parseInt(newPlayerId);
        boolean updated = videoGameService.updateOwner(videoGameId, newPlayer);

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
