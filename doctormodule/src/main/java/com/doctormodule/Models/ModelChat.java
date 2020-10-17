package com.doctormodule.Models;

import java.io.Serializable;

public class ModelChat implements Serializable {
    String id,name,image,no_of_message,last_message,last_image,time_ago;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNo_of_message() {
        return no_of_message;
    }

    public void setNo_of_message(String no_of_message) {
        this.no_of_message = no_of_message;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_image() {
        return last_image;
    }

    public void setLast_image(String last_image) {
        this.last_image = last_image;
    }

    public String getTime_ago() {
        return time_ago;
    }

    public void setTime_ago(String time_ago) {
        this.time_ago = time_ago;
    }
}
