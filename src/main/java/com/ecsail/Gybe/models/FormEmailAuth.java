package com.ecsail.Gybe.models;



public class FormEmailAuth {

    private int id;
    private String email;
    private String host;
    private int port;
    private String user;
    private String pass;
    private String protocol;
    private boolean smtp_auth;
    private boolean ttls;
    private boolean debug;

    public FormEmailAuth() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isSmtp_auth() {
        return smtp_auth;
    }

    public void setSmtp_auth(boolean smtp_auth) {
        this.smtp_auth = smtp_auth;
    }

    public boolean isTtls() {
        return ttls;
    }

    public void setTtls(boolean ttls) {
        this.ttls = ttls;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "FormEmailAuth{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", protocol='" + protocol + '\'' +
                ", smtp_auth=" + smtp_auth +
                ", ttls=" + ttls +
                ", debug=" + debug +
                '}';
    }
}
