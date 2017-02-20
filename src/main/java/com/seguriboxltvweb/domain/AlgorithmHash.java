
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
 *Clase entidad de AlgorithmHash que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "AlgorithmHash")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlgorithmHash.findAll", query = "SELECT a FROM AlgorithmHash a"),
    @NamedQuery(name = "AlgorithmHash.findByAlgorithmHashId", query = "SELECT a FROM AlgorithmHash a WHERE a.algorithmHashId = :algorithmHashId"),
    @NamedQuery(name = "AlgorithmHash.findByReferenceDate", query = "SELECT a FROM AlgorithmHash a WHERE a.referenceDate = :referenceDate"),
    @NamedQuery(name = "AlgorithmHash.findByExpirationDate", query = "SELECT a FROM AlgorithmHash a WHERE a.expirationDate = :expirationDate"),
    @NamedQuery(name = "AlgorithmHash.findByAlgorithmName", query = "SELECT a FROM AlgorithmHash a WHERE a.algorithmName = :algorithmName"),
    @NamedQuery(name = "AlgorithmHash.findByOid", query = "SELECT a FROM AlgorithmHash a WHERE a.oid = :oid"),
    @NamedQuery(name = "AlgorithmHash.findByBits", query = "SELECT a FROM AlgorithmHash a WHERE a.bits = :bits"),
    @NamedQuery(name = "AlgorithmHash.findByBytes", query = "SELECT a FROM AlgorithmHash a WHERE a.bytes = :bytes"),
    @NamedQuery(name = "AlgorithmHash.findBySecure", query = "SELECT a FROM AlgorithmHash a WHERE a.secure = :secure"),
    @NamedQuery(name = "AlgorithmHash.findByIsActive", query = "SELECT a FROM AlgorithmHash a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "AlgorithmHash.findByBinOid", query = "SELECT a FROM AlgorithmHash a WHERE a.binOid = :binOid")})
public class AlgorithmHash implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AlgorithmHashId")
    private Integer algorithmHashId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Column(name = "ExpirationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Size(max = 50)
    @Column(name = "AlgorithmName")
    private String algorithmName;
    @Size(max = 50)
    @Column(name = "Oid")
    private String oid;
    @Column(name = "Bits")
    private Integer bits;
    @Column(name = "Bytes")
    private Integer bytes;
    @Column(name = "Secure")
    private Integer secure;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;
    @Size(max = 50)
    @Column(name = "BinOid")
    private String binOid;

    public AlgorithmHash() {
    }

    public AlgorithmHash(Integer algorithmHashId) {
        this.algorithmHashId = algorithmHashId;
    }

    public AlgorithmHash(Integer algorithmHashId, Date referenceDate, boolean isActive) {
        this.algorithmHashId = algorithmHashId;
        this.referenceDate = referenceDate;
        this.isActive = isActive;
    }

    public Integer getAlgorithmHashId() {
        return algorithmHashId;
    }

    public void setAlgorithmHashId(Integer algorithmHashId) {
        this.algorithmHashId = algorithmHashId;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getBits() {
        return bits;
    }

    public void setBits(Integer bits) {
        this.bits = bits;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public Integer getSecure() {
        return secure;
    }

    public void setSecure(Integer secure) {
        this.secure = secure;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getBinOid() {
        return binOid;
    }

    public void setBinOid(String binOid) {
        this.binOid = binOid;
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
        hash += (algorithmHashId != null ? algorithmHashId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlgorithmHash)) {
            return false;
        }
        AlgorithmHash other = (AlgorithmHash) object;
        if ((this.algorithmHashId == null && other.algorithmHashId != null) || (this.algorithmHashId != null && !this.algorithmHashId.equals(other.algorithmHashId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvapi.domain.AlgorithmHash[ algorithmHashId=" + algorithmHashId + " ]";
    }
    
}
