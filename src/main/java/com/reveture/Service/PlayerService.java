package com.reveture.Service;

import com.reveture.DAO.PlayerDAO;
import com.reveture.Exception.CustomException;
import com.reveture.Model.Player;

public class PlayerService {

    PlayerDAO playerDAO;

    public PlayerService() {
        this.playerDAO = new PlayerDAO();
    }

    public Player createNewPlayer(Player player) throws CustomException {
        if (player == null || player.getUsername().isEmpty()) {
            throw new CustomException("Enter valid username and password in body.");
        }
        else if (player.getPassword().length() < 5) {
            throw new CustomException("Password must be at least 5 characters long.");
        }
        else if (this.playerDAO.checkPlayerByUsername(player.getUsername())) {
            throw new CustomException("Username already exists");
        }

        return this.playerDAO.createNewPlayer(player);
    }

    public Player verifyPlayerCredentials(Player player) throws CustomException {
        if (player == null || player.getUsername().isEmpty() || player.getPassword().length() < 5) {
            throw new CustomException("Enter valid username and password in body.");
        }

        return this.playerDAO.varifyPlayer(player.getUsername(), player.getPassword());
    }
}
