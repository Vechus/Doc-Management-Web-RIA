package it.polimi.tiw.dao;

import it.polimi.tiw.beans.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User checkCredentials(String username, String password) throws SQLException {
        String hashedPassword = DigestUtils.sha512Hex(password);
        String query = "SELECT  id, username, name, surname FROM user  WHERE username = ? AND password =?";
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
                    return user;
                }
            }
        }
    }
}
