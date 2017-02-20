
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
 *Clase entidad de Groups que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "Groups")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Groups.findByGroupId", query = "SELECT g FROM Groups g WHERE g.groupId = :groupId"),
    @NamedQuery(name = "Groups.findByCreatedByUserId", query = "SELECT g FROM Groups g WHERE g.createdByUserId = :createdByUserId"),
    @NamedQuery(name = "Groups.findByGroupName", query = "SELECT g FROM Groups g WHERE g.groupName = :groupName"),
    @NamedQuery(name = "Groups.findByDescription", query = "SELECT g FROM Groups g WHERE g.description = :description"),
    @NamedQuery(name = "Groups.findByIsActive", query = "SELECT g FROM Groups g WHERE g.isActive = :isActive"),
    @NamedQuery(name = "Groups.findByReferenceDate", query = "SELECT g FROM Groups g WHERE g.referenceDate = :referenceDate"),
    @NamedQuery(name = "Groups.findByProfileType", query = "SELECT g FROM Groups g WHERE g.profileType = :profileType"),
    @NamedQuery(name = "Groups.findByPreconfigured", query = "SELECT g FROM Groups g WHERE g.preconfigured = :preconfigured"),
    @NamedQuery(name = "Groups.findByAreaId", query = "SELECT g FROM Groups g WHERE g.areaId = :areaId"),
    @NamedQuery(name = "Groups.findByDeactivatedDate", query = "SELECT g FROM Groups g WHERE g.deactivatedDate = :deactivatedDate"),
    @NamedQuery(name = "Groups.findByDeactivatedByUserId", query = "SELECT g FROM Groups g WHERE g.deactivatedByUserId = :deactivatedByUserId")})
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupId")
    private Short groupId;
    @Column(name = "CreatedByUserId")
    private Integer createdByUserId;
    @Size(max = 50)
    @Column(name = "GroupName")
    private String groupName;
    @Size(max = 500)
    @Column(name = "Description")
    private String description;
    @Column(name = "IsActive")
    private Boolean isActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProfileType")
    private short profileType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Preconfigured")
    private boolean preconfigured;
    @Column(name = "AreaId")
    private Short areaId;
    @Column(name = "DeactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;
    @Column(name = "DeactivatedByUserId")
    private Integer deactivatedByUserId;

    public Groups() {
    }

    public Groups(Short groupId) {
        this.groupId = groupId;
    }

    public Groups(Short groupId, Date referenceDate, short profileType, boolean preconfigured) {
        this.groupId = groupId;
        this.referenceDate = referenceDate;
        this.profileType = profileType;
        this.preconfigured = preconfigured;
    }

    public Short getGroupId() {
        return groupId;
    }

    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public short getProfileType() {
        return profileType;
    }

    public void setProfileType(short profileType) {
        this.profileType = profileType;
    }

    public boolean getPreconfigured() {
        return preconfigured;
    }

    public void setPreconfigured(boolean preconfigured) {
        this.preconfigured = preconfigured;
    }

    public Short getAreaId() {
        return areaId;
    }

    public void setAreaId(Short areaId) {
        this.areaId = areaId;
    }

    public Date getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public Integer getDeactivatedByUserId() {
        return deactivatedByUserId;
    }

    public void setDeactivatedByUserId(Integer deactivatedByUserId) {
        this.deactivatedByUserId = deactivatedByUserId;
    }
    
    public String profileTypeStr(){
        String str="";
        if(profileType==1){
        
            str="Administradores";
            
        }else if(profileType==2){
        
            str="Usuarios";
        }
        
        return str;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.Groups[ groupId=" + groupId + " ]";
    }
    
}
