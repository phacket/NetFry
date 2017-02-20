/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguriboxltvweb.jsf.util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author inggerman
 */
@Named(value = "redirectUtil")
@SessionScoped
public class RedirectUtil implements Serializable {

    /**
     * Creates a new instance of RedirectUtil
     */
    public RedirectUtil() {
        
    }
    
    public String goToMenuMonitoring(){
    
        return "menumonitoring?faces-redirect=true";
    }
    
    public String goToMenuAdminPro(){
    
        return "menuadminprocess?faces-redirect=true";
    }
    public String goToMenuPoli(){
    
        return "menupolicies?faces-redirect=true";
    }
    public String goToMenuReports(){
    
        return "menureports?faces-redirect=true";
    }
    public String goToAdminUser(){
    
        return "adminuseradmin?faces-redirect=true";
    }
    public String goToAdminUserPass(){
    
        return "adminuserpassword?faces-redirect=true";
    }
    public String goToAdminUserCert(){
    
        return "adminusercertificate?faces-redirect=true";
    }
}
