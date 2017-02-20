
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de UserLogReport que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@XmlRootElement
public class UserLogReport implements Serializable {
    @Id
    private Integer recordId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    private int userId;
    private short statusId;
    private String iPAddress;
    private String notes;
    private Integer duration;
    private Short actionType;
    private String userName;
    private String fullName;
    private String tipoUsuario;
    private String hashcertificate;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public short getStatusId() {
        return statusId;
    }

    public void setStatusId(short statusId) {
        this.statusId = statusId;
    }

    public String getiPAddress() {
        return iPAddress;
    }

    public void setiPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Short getActionType() {
        return actionType;
    }

    public void setActionType(Short actionType) {
        this.actionType = actionType;
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

    public String getHashcertificate() {
        return hashcertificate;
    }

    public void setHashcertificate(String hashcertificate) {
        this.hashcertificate = hashcertificate;
    }

    
}
