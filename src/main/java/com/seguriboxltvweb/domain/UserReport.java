
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de UserReport que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@XmlRootElement
public class UserReport implements Serializable {

    @Id
    private int userId;
    private String userName;
    private String fullName;
    private String areaName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    private String tipoUsuario;
    private String tipoInicio;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)

    private Date vencimiento;
    private String serie;
    private String hashCertificate;

    public UserReport() {
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }
    
    
    
    

    public UserReport(int userId) {
        this.userId = userId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoInicio() {
        return tipoInicio;
    }

    public void setTipoInicio(String tipoInicio) {
        this.tipoInicio = tipoInicio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getHashCertificate() {
        return hashCertificate;
    }

    public void setHashCertificate(String hashCertificate) {
        this.hashCertificate = hashCertificate;
    }

}
