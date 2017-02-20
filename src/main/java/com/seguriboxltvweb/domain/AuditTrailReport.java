
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase AuditTrailReport que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@XmlRootElement
public class AuditTrailReport implements Serializable {

    @Id
    private int recordId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    private String eventCode;
    private String categoryCode;
    private String fullNameUser;
    private String userHostIP;
    private String ObjectName;
    private int objectId;
    private String instanceName;
    private String notes;

    public String getUserHostIP() {
        return userHostIP;
    }

    public void setUserHostIP(String userHostIP) {
        this.userHostIP = userHostIP;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }




    public String getObjectName() {
        return ObjectName;
    }

    public void setObjectName(String ObjectName) {
        this.ObjectName = ObjectName;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

}
