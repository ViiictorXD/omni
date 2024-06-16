package com.vitu.omni.database.storage.model.base;

/**
 * BaseEntity
 * <p>
 * UUID = unique identification
 * references = how many objects reference this object
 * 1 = only the cache system and probably will
 * be unloaded soon
 */
public class BaseEntity {

    protected Integer id;
    private Integer references;
    private long lastCheck;
    private long lastModified;
    private boolean modified;
    private boolean removed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    public Integer getReferences() {
        return references;
    }

    public void setReferences(Integer references) {
        this.references = references;
    }

    public void addReference() {
        if (references == 0) loadReferences();
        this.references++;
    }

    public void removeReference() {
        this.references--;
        if (references == 0) unloadReferences();
    }

    public void loadReferences() {

    }

    public void unloadReferences() {

    }

    public long getLastCheck() {
        return System.currentTimeMillis() - lastCheck;
    }

    public void refreshLastCheck() {
        this.lastCheck = System.currentTimeMillis();
    }

    public boolean match(Integer id) {
        return id != null && id.equals(this.id);
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
        this.lastModified = System.currentTimeMillis();
    }

    public long getLastModified() {
        return lastModified;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void remove() {
        this.removed = true;
        this.references = 1;
    }
}