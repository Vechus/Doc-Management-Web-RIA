package it.polimi.tiw.beans;

import java.sql.Date;

/**
 * The type Document.
 */
public class Document {
    private int id;
    private int subfolderId;
    private String name;
    private Date creationDate;
    private String summary;

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
     * Gets subfolder id.
     *
     * @return the subfolder id
     */
    public int getSubfolderId() {
        return subfolderId;
    }

    /**
     * Sets subfolder id.
     *
     * @param subfolderId the subfolder id
     */
    public void setSubfolderId(int subfolderId) {
        this.subfolderId = subfolderId;
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
     * Gets summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
