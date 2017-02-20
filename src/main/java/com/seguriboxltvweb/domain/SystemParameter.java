
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de SystemParameter que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "SystemParameter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SystemParameter.findAll", query = "SELECT s FROM SystemParameter s"),
    @NamedQuery(name = "SystemParameter.findByParameterName", query = "SELECT s FROM SystemParameter s WHERE s.parameterName = :parameterName"),
    @NamedQuery(name = "SystemParameter.findByParameterValue", query = "SELECT s FROM SystemParameter s WHERE s.parameterValue = :parameterValue"),
    @NamedQuery(name = "SystemParameter.findByCategory", query = "SELECT s FROM SystemParameter s WHERE s.category = :category")})
public class SystemParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ParameterName")
    private String parameterName;
    @Size(max = 10000)
    @Column(name = "ParameterValue")
    private String parameterValue;
    @Size(max = 50)
    @Column(name = "Category")
    private String category;
    private boolean anonymous;
    private boolean ssl;


    public SystemParameter() {
    }

    public boolean isAnonymous() {
        
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isSsl() {
        
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
    
    

    public SystemParameter(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parameterName != null ? parameterName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemParameter)) {
            return false;
        }
        SystemParameter other = (SystemParameter) object;
        if ((this.parameterName == null && other.parameterName != null) || (this.parameterName != null && !this.parameterName.equals(other.parameterName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.SystemParameter[ parameterName=" + parameterName + " ]";
    }
    
}
