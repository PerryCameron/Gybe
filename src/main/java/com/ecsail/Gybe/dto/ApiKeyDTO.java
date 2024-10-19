package com.ecsail.Gybe.dto;

public class ApiKeyDTO {
    private final int id;
    String apiName;
    String apiKey;

    public ApiKeyDTO(int id, String apiName, String apiKey) {
        this.id = id;
        this.apiName = apiName;
        this.apiKey = apiKey;
    }

    public int getId() {
        return id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
