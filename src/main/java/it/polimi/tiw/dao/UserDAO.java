package it.polimi.tiw.dao;

import it.polimi.tiw.beans.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
    private final Connection connection;

    /**
     * Instantiates a new User DAO.
     *
     * @param connection the connection object
     */
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Check user credentials.
     *
     * @param username the username
     * @param password the password
     * @return the user
     * @throws SQLException if any error occurs
     */
    public User checkCredentials(String username, String password) throws SQLException {
        String hashedPassword = DigestUtils.sha512Hex(password);
        String query = "SELECT id, username, email FROM user  WHERE username = ? AND password =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.isBeforeFirst())
                    return null;
                else {
                    result.next();
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setUsername(result.getString("username"));
                    user.setEmail(result.getString("email"));
                    System.out.println("User found: " + user.getUsername());
                    return user;
                }
            }
        }
    }

    /**
     * Checks if username already exists
     *
     * @param username the username
     * @return true if username does not exists, false if it does exist.
     * @throws SQLException if any error occurs
     */
    public boolean checkUsername(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.isBeforeFirst();
            }
        }
    }

    /**
     * Checks if email already exists
     *
     * @param email the email
     * @return true if email does not exists, false if it does exist.
     * @throws SQLException the sql exception
     */
    public boolean checkEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.isBeforeFirst();
            }
        }
    }

    /**
     * Register user.
     *
     * @param email    the email
     * @param username the username
     * @param password the password
     * @throws SQLException if any error occurs
     */
    public void registerUser(String email, String username, String password) throws SQLException {
        String hashedPassword = DigestUtils.sha512Hex(password);
        String query = "INSERT INTO user (email, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.executeUpdate();
        }
    }
}
