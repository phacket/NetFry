package com.seguriboxltvweb.domain;

import java.io.Serializable;

/**
 *Clase entidad de UserSession que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
public class UserSession implements Serializable {

    private String sessionId;
    private String userName;
    private String fullName;

    public UserSession() {
        this.fullName = null;
        this.sessionId = null;
        this.userName = null;
    }

    public UserSession(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public UserSession(String userName, String fullName) {
        this.userName = userName;
        this.fullName = fullName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
}
