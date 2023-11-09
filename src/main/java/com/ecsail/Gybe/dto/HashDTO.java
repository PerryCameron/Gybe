package com.ecsail.Gybe.dto;

public class HashDTO {
    private int hash_id;
    private long hash;
    private int ms_id;

    public HashDTO(int hash_id, int ms_id, String email) {
        this.hash_id = hash_id;
        this.hash = (email + ms_id).hashCode();
        this.ms_id = ms_id;
    }

    public HashDTO(int hash_id, long hash, int ms_id) {
        this.hash_id = hash_id;
        this.hash = hash;
        this.ms_id = ms_id;
    }

    public HashDTO() {
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

    public int getMs_id() {
        return ms_id;
    }

    public void setMs_id(int ms_id) {
        this.ms_id = ms_id;
    }

    @Override
    public String toString() {
        return "HashDTO{" +
                "hash_id=" + hash_id +
                ", hash=" + hash +
                ", msid=" + ms_id +
                '}';
    }
}
