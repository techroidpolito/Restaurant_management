package com.gratus.ownerapp.model;

import java.io.Serializable;

public class UserProfileRegistrationModel implements Serializable {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String description;
    private String restName;
    private String covePhoto;

    public UserProfileRegistrationModel(String name, String phone, String email, String address, String description, String restName, String covePhoto) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.restName = restName;
        this.covePhoto = covePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getCovePhoto() {
        return covePhoto;
    }

    public void setCovePhoto(String covePhoto) {
        this.covePhoto = covePhoto;
    }

}
