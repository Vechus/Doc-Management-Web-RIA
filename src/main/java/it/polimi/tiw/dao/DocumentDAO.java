package it.polimi.tiw.dao;

import it.polimi.tiw.beans.Document;
import it.polimi.tiw.beans.DocumentData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentDAO {
    private final Connection connection;
    private final int id;

    public DocumentDAO(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    public void moveDocument(int subfolderId) throws SQLException {
        String query = "UPDATE document SET subfolder_id= ? WHERE id= ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, subfolderId);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public Document findDocument() throws SQLException {
        Document document = new Document();
        String query = "SELECT * FROM document WHERE id= ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                document.setId(resultSet.getInt(1));
                document.setSubfolderId(resultSet.getInt(2));
                document.setName(resultSet.getString(3));
                document.setCreationDate(resultSet.getDate(4));
                document.setSummary(resultSet.getString(5));
            }
        }
        return document;
    }

    public DocumentData getData() throws SQLException {
        DocumentData documentData = new DocumentData();
        String query = "SELECT name, summary, data FROM document WHERE id= ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                documentData.setId(id);
                documentData.setName(resultSet.getString(1));
                documentData.setSummary(resultSet.getString(2));
                documentData.setData(resultSet.getString(3));
            }
        }
        return documentData;
    }

    public void deleteDocument() throws SQLException {
        String query = "DELETE FROM document WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
