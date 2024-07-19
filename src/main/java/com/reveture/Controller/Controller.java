package com.reveture.Controller;

import com.reveture.Exception.CustomException;
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

        try {
            Player newlyCreatedPlayer = playerService.createNewPlayer(player);
            context.json(newlyCreatedPlayer);
        } catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
        }

    }

    public void login(Context context) {
        Player player = context.bodyAsClass(Player.class);

        try {
            Player varifiedPlayer = playerService.verifyPlayerCredentials(player);
            context.json(varifiedPlayer);
        } catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    private void getAllVideogames(Context context) {
        try {
            ArrayList<VideoGames> videogames = videoGameService.getAllVideoGames();
            context.json(videogames);
        }
        catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    private void getVideogameById(Context context) {
        int gameId = Integer.parseInt(context.pathParam("gameId"));

        try {
            VideoGames videogames = videoGameService.getVideoGameById(gameId);
            context.json(videogames);
        }
        catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    private void createVideoGame(Context context) {
        VideoGames videogame = context.bodyAsClass(VideoGames.class);

        try {
            VideoGames createdVideogame = videoGameService.createVideoGame(videogame);
            context.json(createdVideogame);
        }
        catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    private void deleteGame(Context context) {
        int videoGameId = Integer.parseInt(context.pathParam("gameId"));

        try {
            boolean deleted = videoGameService.deleteVideoGame(videoGameId);
            if (deleted) {
                context.result("Videogame successfully deleted");
            }
            else {
                context.status(500);
                context.result("Unexpected error occurred");
            }
        }
        catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
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
        try {
            boolean updated = videoGameService.updateOwner(videoGameId, newPlayer);
            if (updated) {
                context.result("Videogame owner successfully updated");
            }
            else {
                context.status(500);
                context.result("Unexpected error occurred");
            }
        }
        catch (CustomException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    private void getVideogamesByOwner(Context context) {
        int playerId = Integer.parseInt(context.pathParam("playerId"));

        try {
            ArrayList<VideoGames> videoGames = videoGameService.getVideogamesByOwner(playerId);
            context.json(videoGames);
        }
        catch (CustomException e) {
            context.result(e.getMessage());
        }
    }

}
