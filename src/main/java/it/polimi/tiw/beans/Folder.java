package it.polimi.tiw.beans;

import java.sql.Date;
import java.util.List;

public class Folder {
    private int id;
    private String name;
    private Date creationDate;
    private List<Subfolder> subfolders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Subfolder> getSubfolders() {
        return subfolders;
    }

    public void setSubfolders(List<Subfolder> subfolders) {
        this.subfolders = subfolders;
    }
}
