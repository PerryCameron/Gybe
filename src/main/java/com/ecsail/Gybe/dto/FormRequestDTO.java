package com.ecsail.Gybe.dto;

public class FormRequestDTO {
    int form_id;
    String req_date;
    String primaryMember;
    int msid;
    boolean success;

    public FormRequestDTO(int form_id, String pri_mem, int msid, boolean success) {
        this.form_id = form_id;
        this.req_date = "auto";
        this.primaryMember = pri_mem;
        this.msid = msid;
        this.success = success;
    }

    public FormRequestDTO() {
    }

    public FormRequestDTO(String pri_mem, int msid, boolean success) {
        this.form_id = 0;
        this.req_date = "auto";
        this.primaryMember = pri_mem;
        this.msid = msid;
        this.success = success;
    }

    public int getForm_id() {
        return form_id;
    }

    public void setForm_id(int form_id) {
        this.form_id = form_id;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getPrimaryMember() {
        return primaryMember;
    }

    public void setPrimaryMember(String primaryMember) {
        this.primaryMember = primaryMember;
    }

    public int getMsid() {
        return msid;
    }

    public void setMsid(int msid) {
        this.msid = msid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

//    public void generateKey() {
//        this.form_id = SqlKey.getNextPrimaryKey("form_request","FORM_ID");
//    }
}
