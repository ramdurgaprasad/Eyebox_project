package com.example.Eyebox.model;

import org.springframework.beans.factory.annotation.Value;

public class Properties {
    @Value("${host}")
    String host;
    @Value("${sender}")
    String sender;
    @Value("${port}")
    String port;
    @Value("${password}")
    String password;
    @Value("${reset.linkMessage}")
    String resetMessage;
    @Value("${update.PasswordMessage}")
    String updatePasswordMessage;

    public String getPassword() {
        return password;
    }

    public String getResetMessage() {
        return resetMessage;
    }

    public String getUpdatePasswordMessage() {
        return updatePasswordMessage;
    }

    public String getHost() {
        return host;
    }

    public String getSender() {
        return sender;
    }

    public String getPort() {
        return port;
    }
}
