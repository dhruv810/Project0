package com.reveture.DAO;

import com.reveture.Exception.CustomException;
import com.reveture.Model.Player;
import com.reveture.Util.ConnectionUtil;

import java.sql.*;

public class PlayerDAO {
    public Player createNewPlayer(Player player) throws CustomException {
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO player (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getUsername());
            ps.setString(2, player.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return new Player(rs.getInt("playerId"),
                                            rs.getString("username"),
                                            rs.getString("password"));
            }
            else {
                throw new CustomException("Failed to create new player");
            }

        } catch (SQLException e) {
            throw new CustomException("SQL exception occurred: " + e.getMessage());
        }
    }

    public boolean checkPlayerByUsername(String username) throws CustomException {
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM player WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new CustomException("SQL exception occurred: " + e.getMessage());
        }
        return false;
    }

    public Player varifyPlayer(String username, String password) throws CustomException {
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM player WHERE username = ? and password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Player(rs.getInt("playerId"),
                        rs.getString("username"),
                        rs.getString("password"));
            } else {
                throw new CustomException("Please enter valid username and password");
            }

        } catch (SQLException e) {
            throw new CustomException("SQL exception occurred: " + e.getMessage());
        }
    }

    public boolean checkPlayerByPlayerId(int owner) {
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM player WHERE playerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, owner);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return true;
    }
}
