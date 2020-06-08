package it.polimi.tiw.dao;

import it.polimi.tiw.beans.Document;
import it.polimi.tiw.beans.Subfolder;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SubfolderDAO {
    private final Connection connection;
    private final int id;

    /**
     * Instantiates a new Subfolder DAO.
     *
     * @param connection the connection
     * @param id         the id
     */
    public SubfolderDAO(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    /**
     * Find documents relative to this subfolder.
     *
     * @return the list of documents contained in this subfolder
     * @throws SQLException if any error occurs
     */
    public List<Document> findDocuments() throws SQLException {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT id, subfolder_id, name, creation_date, summary FROM document WHERE subfolder_id= ? ORDER BY name ASC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Document document = new Document();
                    document.setId(resultSet.getInt(1));
                    document.setSubfolderId(resultSet.getInt(2));
                    document.setName(resultSet.getString(3));
                    document.setCreationDate(resultSet.getDate(4));
                    document.setSummary(resultSet.getString(5));
                    documents.add(document);
                }
            }
        }
        return documents;
    }

    /**
     * Find subfolder.
     *
     * @return the subfolder
     * @throws SQLException if any error occurs
     */
    public Subfolder findSubfolder() throws SQLException {
        Subfolder subfolder = new Subfolder();
        String query = "SELECT * FROM subfolder WHERE id= ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                subfolder.setId(resultSet.getInt(1));
                subfolder.setFolderId(resultSet.getInt(2));
                subfolder.setName(resultSet.getString(3));
                subfolder.setCreationDate(resultSet.getDate(4));
            }
        }
        return subfolder;
    }

    /**
     * Move subfolder to a folder.
     *
     * @param folderId the folder id
     * @throws SQLException if any error occurs
     */
    public void moveSubfolder(int folderId) throws SQLException {
        String query = "UPDATE subfolder SET folder_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, folderId);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete subfolder.
     *
     * @throws SQLException if any error occurs
     */
    public void deleteSubfolder() throws SQLException {
        String query = "DELETE FROM subfolder WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
