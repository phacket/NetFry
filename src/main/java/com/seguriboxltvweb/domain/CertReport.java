package com.seguriboxltvweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase CertReport que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */

@Entity
@XmlRootElement
public class CertReport implements Serializable {

    @Id
    private int id;
    private int userId;
    private String fullName;
    private String email;
    private String certificateSerie;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    private int userType;
    private String userTypeStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCertificateSerie() {
        return certificateSerie;
    }

    public void setCertificateSerie(String certificateSerie) {
        this.certificateSerie = certificateSerie;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserTypeStr() {
        if(getUserType() == 0) {
            userTypeStr = "Administrador de plataforma";
        } else if(getUserType() == 1) {
            userTypeStr = "Administrador";
        } else if(getUserType() == 2) {
            userTypeStr = "Usuario";
        }
        return userTypeStr;
    }

    public void setUserTypeStr(String userTypeStr) {
        this.userTypeStr = userTypeStr;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
