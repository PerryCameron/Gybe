package com.ecsail.Gybe.dto;

import java.sql.Timestamp;

public class AppSettingsDTO {
    private String key;
    private String value;
    private String description;
    private String dataType;
    private Timestamp dateTime;
    private String groupName;

    public AppSettingsDTO(String key, String value, String description, String dataType, Timestamp dateTime, String groupName) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.dataType = dataType;
        this.dateTime = dateTime;
        this.groupName = groupName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
