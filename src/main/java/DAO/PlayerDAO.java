package DAO;

import Model.Player;
import Util.ConnectionUtil;

import java.sql.*;

public class PlayerDAO {
    public Player createNewPlayer(Player player) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO player (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getUsername());
            ps.setString(2, player.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Player newPlayer = new Player(rs.getInt("playerId"), player.getUsername(), player.getPassword());
                System.out.println(newPlayer);
                return newPlayer;
            }

        } catch (SQLException e) {
            System.out.println("SQL exception occur: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Other exception occur: " + e.getMessage());
        }
        return null;
    }

    public boolean checkPlayerByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM player WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("SQL exception occur: " + e.getMessage());
        }
        return false;
    }

    public Player varifyPlayer(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM player WHERE username = ? and password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Player(rs.getInt("playerId"), username, password);
            }

        } catch (SQLException e) {
            System.out.println("SQL exception occur: " + e.getMessage());
        }
        return null;
    }

    public boolean checkPlayerByPlayerId(int owner) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM player WHERE playerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, owner);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return false;
    }
}
