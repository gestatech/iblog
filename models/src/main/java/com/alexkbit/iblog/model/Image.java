package com.alexkbit.iblog.model;

/**
 * Model of image
 */
public class Image extends TimeMarkModel {

    /**
     * Owner
     */
    private User user;

    /**
     * Name of image
     */
    private String name;

    /**
     * Data of image
     */
    private byte[] data;

    /**
     * Type of image
     */
    private ImageType type;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }
}
