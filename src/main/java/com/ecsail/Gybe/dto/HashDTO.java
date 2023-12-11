package com.ecsail.Gybe.dto;

public class HashDTO {
    private int hash_id;
    private long hash;
    private int msId;

    public HashDTO(int hash_id, int msId, String email) {
        this.hash_id = hash_id;
        this.hash = (email + msId).hashCode();
        this.msId = msId;
    }

    public HashDTO(int hash_id, long hash, int msId) {
        this.hash_id = hash_id;
        this.hash = hash;
        this.msId = msId;
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

    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    @Override
    public String toString() {
        return "HashDTO{" +
                "hash_id=" + hash_id +
                ", hash=" + hash +
                ", msid=" + msId +
                '}';
    }
}
