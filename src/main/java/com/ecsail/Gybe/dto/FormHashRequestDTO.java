package com.ecsail.Gybe.dto;


// this DTO stores information when a user requests a hash
public class FormHashRequestDTO {
    int form_hash_id;
    String req_date;
    String pri_mem;
    String link;
    int msid;
    String mailed_to;

    public FormHashRequestDTO(int form_hash_id, String primaryMember, String link, int msid, String mailed_to) {
        this.form_hash_id = form_hash_id;
        this.req_date = "auto";
        this.pri_mem = primaryMember;
        this.link = link;
        this.msid = msid;
        this.mailed_to = mailed_to;
    }

    public FormHashRequestDTO(int form_hash_id, String req_date, String pri_mem, String link, int msid, String mailed_to) {
        this.form_hash_id = form_hash_id;
        this.req_date = req_date;
        this.pri_mem = pri_mem;
        this.link = link;
        this.msid = msid;
        this.mailed_to = mailed_to;
    }

    public int getForm_hash_id() {
        return form_hash_id;
    }

    public void setForm_hash_id(int form_hash_id) {
        this.form_hash_id = form_hash_id;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getPri_mem() {
        return pri_mem;
    }

    public void setPri_mem(String pri_mem) {
        this.pri_mem = pri_mem;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getMsid() {
        return msid;
    }

    public void setMsid(int msid) {
        this.msid = msid;
    }

    public String getMailed_to() {
        return mailed_to;
    }

    public void setMailed_to(String mailed_to) {
        this.mailed_to = mailed_to;
    }

}
