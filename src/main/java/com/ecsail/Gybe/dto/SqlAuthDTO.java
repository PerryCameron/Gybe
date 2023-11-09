package com.ecsail.Gybe.dto;

public class SqlAuthDTO {

    private String user;
    private String pass;
    private String ip;
    private String port;
    private String db_name;

    private String encrypt_pass;

    public SqlAuthDTO(String user, String pass, String ip, String port, String db_name, String encrypt_pass) {
        this.user = user;
        this.pass = pass;
        this.ip = ip;
        this.port = port;
        this.db_name = db_name;
        this.encrypt_pass = encrypt_pass;
    }

    public SqlAuthDTO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getEncrypt_pass() {
        return encrypt_pass;
    }

    public void setEncrypt_pass(String encrypt_pass) {
        this.encrypt_pass = encrypt_pass;
    }
}
