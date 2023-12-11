package com.ecsail.Gybe.dto;

public class FormSettingsDTO {
    int port;
    String link;
    String form_url;
    String form_id;
    int selected_year;

    public FormSettingsDTO(int port, String link, String form_url, String form_id, int selected_year) {
        this.port = port;
        this.link = link;
        this.form_url = form_url;
        this.form_id = form_id;
        this.selected_year = selected_year;
    }

    public FormSettingsDTO() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getForm_url() {
        return form_url;
    }

    public void setForm_url(String form_url) {
        this.form_url = form_url;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public int getSelected_year() {
        return selected_year;
    }

    public void setSelected_year(int selected_year) {
        this.selected_year = selected_year;
    }

    @Override
    public String toString() {
        return "FormSettingsDTO{" +
                "port=" + port +
                ", link='" + link + '\'' +
                ", form_url='" + form_url + '\'' +
                ", form_id='" + form_id + '\'' +
                ", selected_year=" + selected_year +
                '}';
    }
}
