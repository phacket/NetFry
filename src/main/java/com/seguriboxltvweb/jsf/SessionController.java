package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.jsf.util.SessionUtils;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author hacket
 */
@Named("sessionController")
@SessionScoped
public class SessionController extends SessionUtils implements Serializable {
    
    private String sessionIdUser;
    private String fullNameUser;
    private String userNameUser;

    public String getSessionIdUser() {
        return sessionIdUser;
    }

    public void setSessionIdUser(String sessionIdUser) {
        this.sessionIdUser = sessionIdUser;
    }

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }

    public String getUserNameUser() {
        return userNameUser;
    }

    public void setUserNameUser(String userNameUser) {
        this.userNameUser = userNameUser;
    }

  

    
}
