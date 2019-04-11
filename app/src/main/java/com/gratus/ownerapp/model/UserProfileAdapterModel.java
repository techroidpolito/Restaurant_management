package com.gratus.ownerapp.model;

public class UserProfileAdapterModel {
    private Integer layoutType;
    private String header;
    private String itemString;


    public UserProfileAdapterModel(Integer layoutType, String header,String itemString) {
        this.layoutType = layoutType;
        this.itemString = itemString;
        this.header = header;
    }

    public String getItemString() {
        return itemString;
    }

    public void setItemString(String itemString) {
        this.itemString = itemString;
    }


    public Integer getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(Integer layoutType) {
        this.layoutType = layoutType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
