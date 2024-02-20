package com.ecsail.Gybe.dto;

public class ThemeDTO {
    int id;
    String stickerName;
    String url;
    String yearColor;

    public ThemeDTO(int id, String stickerName, String url, String yearColor) {
        this.id = id;
        this.stickerName = stickerName;
        this.url = url;
        this.yearColor = yearColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYearColor() {
        return yearColor;
    }

    public void setYearColor(String yearColor) {
        this.yearColor = yearColor;
    }

    @Override
    public String toString() {
        return "themeDTO{" +
                "id=" + id +
                ", stickerName='" + stickerName + '\'' +
                ", url='" + url + '\'' +
                ", yearColor='" + yearColor + '\'' +
                '}';
    }
}
