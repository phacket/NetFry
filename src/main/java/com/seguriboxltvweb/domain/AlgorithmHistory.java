
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de AlgorithmHistory que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "AlgorithmHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlgorithmHistory.findAll", query = "SELECT a FROM AlgorithmHistory a"),
    @NamedQuery(name = "AlgorithmHistory.findByEventId", query = "SELECT a FROM AlgorithmHistory a WHERE a.eventId = :eventId"),
    @NamedQuery(name = "AlgorithmHistory.findByReferenceDate", query = "SELECT a FROM AlgorithmHistory a WHERE a.referenceDate = :referenceDate"),
    @NamedQuery(name = "AlgorithmHistory.findByUserId", query = "SELECT a FROM AlgorithmHistory a WHERE a.userId = :userId"),
    @NamedQuery(name = "AlgorithmHistory.findByHostName", query = "SELECT a FROM AlgorithmHistory a WHERE a.hostName = :hostName"),
    @NamedQuery(name = "AlgorithmHistory.findByAction", query = "SELECT a FROM AlgorithmHistory a WHERE a.action = :action"),
    @NamedQuery(name = "AlgorithmHistory.findByDetails", query = "SELECT a FROM AlgorithmHistory a WHERE a.details = :details")})
public class AlgorithmHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EventId")
    private Integer eventId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Column(name = "UserId")
    private Integer userId;
    @Size(max = 100)
    @Column(name = "HostName")
    private String hostName;
    @Size(max = 50)
    @Column(name = "Action")
    private String action;
    @Size(max = 250)
    @Column(name = "Details")
    private String details;
    
    private String[] detailsSplit;

    public AlgorithmHistory() {
    }

    public AlgorithmHistory(Integer eventId) {
        this.eventId = eventId;
    }
    

    public AlgorithmHistory(Integer eventId, Date referenceDate) {
        this.eventId = eventId;
        this.referenceDate = referenceDate;
    }

    public String[] getDetailsSplit() {
        return detailsSplit;
    }

    public void setDetailsSplit(String[] detailsSplit) {
        this.detailsSplit = detailsSplit;
    }
    
    

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlgorithmHistory)) {
            return false;
        }
        AlgorithmHistory other = (AlgorithmHistory) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.AlgorithmHistory[ eventId=" + eventId + " ]";
    }
    
}
