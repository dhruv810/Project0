package DAO;

import Model.VideoGames;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class VideoGameDAO {
    public ArrayList<VideoGames> getAllVideoGames() {
        ArrayList<VideoGames> arraylist = new ArrayList<>();

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM videogames";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                VideoGames tmp = new VideoGames(rs.getInt("gameId"),
                                                rs.getString("gameName"),
                                                rs.getDouble("price"),
                                                rs.getInt("owner"));
                arraylist.add(tmp);
            }

        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        }

        return arraylist;
    }

    public VideoGames getVideoGameById(int gameId) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM videogames WHERE gameId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, gameId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                return new VideoGames(  rs.getInt("gameId"),
                                        rs.getString("gameName"),
                                        rs.getDouble("price"),
                                        rs.getInt("owner"));
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }

    public boolean checkVideoGameByNameAndOwner(String gameName, int owner) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM videogames WHERE gameName = ? AND owner = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, gameName);
            ps.setInt(2, owner);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return false;
    }

    public VideoGames createVideoGame(VideoGames videogame) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO videogames (gameName, price, owner) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, videogame.getGameName());
            ps.setDouble(2, videogame.getPrice());
            ps.setInt(3, videogame.getOwner());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()) {
                return new VideoGames(  rs.getInt("gameId"),
                                        rs.getString("gameName"),
                                        rs.getDouble("price"),
                                        rs.getInt("owner"));
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }

    public boolean deleteVideoGame(int videoGameId) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM videogames WHERE gameId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, videoGameId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return false;
    }

    public boolean updateVideoGame(int videoGameId, int newPlayer) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE videogames SET owner = ? WHERE gameId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newPlayer);
            ps.setInt(2, videoGameId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return false;
    }

    public ArrayList<VideoGames> getVideoGamesByOwner(int playerId) {
        ArrayList<VideoGames> arrayList = new ArrayList<>();

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM videogames WHERE owner = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, playerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                    VideoGames temp = new VideoGames(   rs.getInt("gameId"),
                                                        rs.getString("gameName"),
                                                        rs.getDouble("price"),
                                                        rs.getInt("owner"));

                arrayList.add(temp);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return arrayList;
    }
}
