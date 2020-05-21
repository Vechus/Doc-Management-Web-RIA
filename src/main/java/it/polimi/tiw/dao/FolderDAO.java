package it.polimi.tiw.dao;

import it.polimi.tiw.beans.Subfolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FolderDAO {
    private final Connection connection;
    private final int id;

    public FolderDAO(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    public List<Subfolder> findSubfolders() throws SQLException {
        List<Subfolder> subfolders = new ArrayList<>();
        String query = "SELECT * FROM subfolder WHERE folder_id= ? ORDER BY name ASC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Subfolder subfolder = new Subfolder();
                    subfolder.setId(resultSet.getInt(1));
                    subfolder.setFolderId(resultSet.getInt(2));
                    subfolder.setName(resultSet.getString(3));
                    subfolder.setCreationDate(resultSet.getDate(4));
                    subfolders.add(subfolder);
                }
            }
        }
        return subfolders;
    }
}
