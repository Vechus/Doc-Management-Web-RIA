package it.polimi.tiw.dao;

import it.polimi.tiw.beans.Folder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HomeDAO {
    private final Connection connection;

    /**
     * Instantiates a new Home DAO.
     *
     * @param connection the connection object
     */
    public HomeDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Find all folders.
     *
     * @return the list of folders
     * @throws SQLException the sql exception
     */
    public List<Folder> findFolders() throws SQLException {
        List<Folder> folders = new ArrayList<>();
        String query = "SELECT * FROM folder ORDER BY name ASC";
        try(ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
            while(resultSet.next()) {
                Folder folder = new Folder();
                folder.setId(resultSet.getInt(1));
                folder.setName(resultSet.getString(2));
                folder.setCreationDate(resultSet.getDate(3));

                FolderDAO folderDAO = new FolderDAO(connection, folder.getId());
                folder.setSubfolders(folderDAO.findSubfolders());
                folders.add(folder);
            }
        }
        return folders;
    }
}
