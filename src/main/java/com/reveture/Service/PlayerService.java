package com.reveture.Service;

import com.reveture.DAO.PlayerDAO;
import com.reveture.Model.Player;

public class PlayerService {

    PlayerDAO playerDAO;

    public PlayerService() {
        this.playerDAO = new PlayerDAO();
    }

    public Player createNewPlayer(Player player) {
        if (player == null || player.getUsername().isEmpty() || player.getPassword().length() < 2 || this.playerDAO.checkPlayerByUsername(player.getUsername())) {
            return null;
        }
        return this.playerDAO.createNewPlayer(player);
    }

    public Player verifyPlayerCredentials(Player player) {
        if (player == null || player.getUsername().isEmpty() || player.getPassword().length() < 2) {
            return null;
        }
        return this.playerDAO.varifyPlayer(player.getUsername(), player.getPassword());
    }
}
