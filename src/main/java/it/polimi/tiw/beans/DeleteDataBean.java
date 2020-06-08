package it.polimi.tiw.beans;

/**
 * The type Delete data bean. It is used in the context of the deletion of an entity.
 */
public class DeleteDataBean {
    private String entity_type;
    private int entity_id;

    /**
     * Gets entity type.
     *
     * @return the entity type
     */
    public String getEntity_type() {
        return entity_type;
    }

    /**
     * Sets entity type.
     *
     * @param entity_type the entity type
     */
    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    /**
     * Gets entity id.
     *
     * @return the entity id
     */
    public int getEntity_id() {
        return entity_id;
    }

    /**
     * Sets entity id.
     *
     * @param entity_id the entity id
     */
    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }
}
