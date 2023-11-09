package com.ecsail.Gybe.dto;


public class FormEmailAuthDTO {

    private String email;
    private String host;
    private int port;
    private String user;
    private String pass;
    private String protocol;
    private boolean smtp_auth;
    private boolean isTTLS;
    private boolean debugMode;

    public FormEmailAuthDTO(String email, String host, int port, String user, String pass, String protocol, boolean smtp_auth, boolean isTTLS, boolean debugMode) {
        this.email = email;
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.protocol = protocol;
        this.smtp_auth = smtp_auth;
        this.isTTLS = isTTLS;
        this.debugMode = debugMode;
    }

    public FormEmailAuthDTO() {
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

    public boolean isTTLS() {
        return isTTLS;
    }

    public void setTTLS(boolean TTLS) {
        isTTLS = TTLS;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public String toString() {
        return "FormEmailAuthDTO{" +
                "email='" + email + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", protocol='" + protocol + '\'' +
                ", smtp_auth=" + smtp_auth +
                ", isTTLS=" + isTTLS +
                ", debugMode=" + debugMode +
                '}';
    }
}
