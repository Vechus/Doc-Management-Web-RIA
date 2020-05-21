package it.polimi.tiw.beans;

import java.sql.Date;

public class Document {
    private int id;
    private int subfolderId;
    private String name;
    private Date creationDate;
    private String summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubfolderId() {
        return subfolderId;
    }

    public void setSubfolderId(int subfolderId) {
        this.subfolderId = subfolderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
