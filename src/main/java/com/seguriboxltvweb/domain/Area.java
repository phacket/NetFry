
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
 *Clase Area que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "Area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByAreaId", query = "SELECT a FROM Area a WHERE a.areaId = :areaId"),
    @NamedQuery(name = "Area.findByAreaName", query = "SELECT a FROM Area a WHERE a.areaName = :areaName"),
    @NamedQuery(name = "Area.findByIsActive", query = "SELECT a FROM Area a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "Area.findByReferenceDate", query = "SELECT a FROM Area a WHERE a.referenceDate = :referenceDate")})
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AreaId")
    private Short areaId;
    @Size(max = 50)
    @Column(name = "AreaName")
    private String areaName;
    @Column(name = "IsActive")
    private Boolean isActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;

    public Area() {
    }

    public Area(Short areaId) {
        this.areaId = areaId;
    }

    public Area(Short areaId, Date referenceDate) {
        this.areaId = areaId;
        this.referenceDate = referenceDate;
    }

    public Short getAreaId() {
        return areaId;
    }

    public void setAreaId(Short areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
    public String StatusString(){
    
        String str="";
        
        if(isActive==true){
            str="Activo";
            
        }else{
            str="Desactivado";
        }
        
        return str;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (areaId != null ? areaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.areaId == null && other.areaId != null) || (this.areaId != null && !this.areaId.equals(other.areaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.Area[ areaId=" + areaId + " ]";
    }
    
}
