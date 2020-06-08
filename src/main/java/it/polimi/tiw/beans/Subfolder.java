package it.polimi.tiw.beans;

import java.sql.Date;
import java.util.List;

/**
 * The type Subfolder.
 */
public class Subfolder {
    private int id;
    private int folderId;
    private String name;
    private Date creationDate;
    private List<Document> documents;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets folder id.
     *
     * @return the folder id
     */
    public int getFolderId() {
        return folderId;
    }

    /**
     * Sets folder id.
     *
     * @param folderId the folder id
     */
    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets documents.
     *
     * @return the documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets documents.
     *
     * @param documents the documents
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
