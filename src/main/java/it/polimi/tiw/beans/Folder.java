package it.polimi.tiw.beans;

import java.sql.Date;
import java.util.List;

/**
 * The type Folder.
 */
public class Folder {
    private int id;
    private String name;
    private Date creationDate;
    private List<Subfolder> subfolders;

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
     * Gets subfolders.
     *
     * @return the subfolders
     */
    public List<Subfolder> getSubfolders() {
        return subfolders;
    }

    /**
     * Sets subfolders.
     *
     * @param subfolders the subfolders
     */
    public void setSubfolders(List<Subfolder> subfolders) {
        this.subfolders = subfolders;
    }
}
