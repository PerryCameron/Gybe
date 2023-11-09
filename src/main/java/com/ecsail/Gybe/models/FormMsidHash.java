package com.ecsail.Gybe.models;

public class FormMsidHash {


    private int hash_id;
    private long hash;

    private int msid;

    public FormMsidHash(int hash_id, int msid, String email) {
        this.hash_id = hash_id;
        this.hash = String.valueOf(email + msid).hashCode();
        this.msid = msid;
    }

    public FormMsidHash(int msid, String email) {
        this.hash = String.valueOf(email + msid).hashCode();
        this.msid = msid;
    }

    public FormMsidHash(int hash_id, long hash, int msid) {
    }

    public FormMsidHash() {
    }


    public int getHash_id() {
        return hash_id;
    }

    public void setHash_id(int hash_id) {
        this.hash_id = hash_id;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public int getMsid() {
        return msid;
    }

    public void setMsid(int msid) {
        this.msid = msid;
    }

    @Override
    public String toString() {
        return "HashMsidDTO{" +
                "id=" + hash_id +
                ", hash=" + hash +
                ", msid=" + msid +
                '}';
    }
}
