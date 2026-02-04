package io.github.khezyapp.checksum.model;

/**
 * Parent class to test field inheritance
 */
public class BaseEntity {
    private long id = 101L;
    protected String status = "ACTIVE";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
