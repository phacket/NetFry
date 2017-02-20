
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de AlgorithmAsymmetric que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "AlgorithmAsymmetric")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlgorithmAsymmetric.findAll", query = "SELECT a FROM AlgorithmAsymmetric a"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByAlgorithmAsymmetricId", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.algorithmAsymmetricId = :algorithmAsymmetricId"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByReferenceDate", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.referenceDate = :referenceDate"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByExpirationDate", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.expirationDate = :expirationDate"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByAlgorithmName", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.algorithmName = :algorithmName"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByOid", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.oid = :oid"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByMinBits", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.minBits = :minBits"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByMaxBits", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.maxBits = :maxBits"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByDeltaBits", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.deltaBits = :deltaBits"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByMinSecureBits", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.minSecureBits = :minSecureBits"),
    @NamedQuery(name = "AlgorithmAsymmetric.findBySecure", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.secure = :secure"),
    @NamedQuery(name = "AlgorithmAsymmetric.findByIsActive", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "AlgorithmAsymmetric.findBySize", query = "SELECT a FROM AlgorithmAsymmetric a WHERE a.size = :size")})
public class AlgorithmAsymmetric implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AlgorithmAsymmetricId")
    private Integer algorithmAsymmetricId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Column(name = "ExpirationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AlgorithmName")
    private String algorithmName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Oid")
    private String oid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "BinOid")
    private String binOid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MinBits")
    private int minBits;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MaxBits")
    private int maxBits;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DeltaBits")
    private int deltaBits;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MinSecureBits")
    private int minSecureBits;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Secure")
    private int secure;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;
    @Column(name = "Size")
    private Integer size;

    public AlgorithmAsymmetric() {
    }

    public AlgorithmAsymmetric(Integer algorithmAsymmetricId) {
        this.algorithmAsymmetricId = algorithmAsymmetricId;
    }

    public AlgorithmAsymmetric(Integer algorithmAsymmetricId, Date referenceDate, String algorithmName, String oid, String binOid, int minBits, int maxBits, int deltaBits, int minSecureBits, int secure, boolean isActive) {
        this.algorithmAsymmetricId = algorithmAsymmetricId;
        this.referenceDate = referenceDate;
        this.algorithmName = algorithmName;
        this.oid = oid;
        this.binOid = binOid;
        this.minBits = minBits;
        this.maxBits = maxBits;
        this.deltaBits = deltaBits;
        this.minSecureBits = minSecureBits;
        this.secure = secure;
        this.isActive = isActive;
    }

    public Integer getAlgorithmAsymmetricId() {
        return algorithmAsymmetricId;
    }

    public void setAlgorithmAsymmetricId(Integer algorithmAsymmetricId) {
        this.algorithmAsymmetricId = algorithmAsymmetricId;
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

    public String getBinOid() {
        return binOid;
    }

    public void setBinOid(String binOid) {
        this.binOid = binOid;
    }

   
    public int getMinBits() {
        return minBits;
    }

    public void setMinBits(int minBits) {
        this.minBits = minBits;
    }

    public int getMaxBits() {
        return maxBits;
    }

    public void setMaxBits(int maxBits) {
        this.maxBits = maxBits;
    }

    public int getDeltaBits() {
        return deltaBits;
    }

    public void setDeltaBits(int deltaBits) {
        this.deltaBits = deltaBits;
    }

    public int getMinSecureBits() {
        return minSecureBits;
    }

    public void setMinSecureBits(int minSecureBits) {
        this.minSecureBits = minSecureBits;
    }

    public int getSecure() {
        return secure;
    }

    public void setSecure(int secure) {
        this.secure = secure;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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
        hash += (algorithmAsymmetricId != null ? algorithmAsymmetricId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlgorithmAsymmetric)) {
            return false;
        }
        AlgorithmAsymmetric other = (AlgorithmAsymmetric) object;
        if ((this.algorithmAsymmetricId == null && other.algorithmAsymmetricId != null) || (this.algorithmAsymmetricId != null && !this.algorithmAsymmetricId.equals(other.algorithmAsymmetricId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvapi.domain.AlgorithmAsymmetric[ algorithmAsymmetricId=" + algorithmAsymmetricId + " ]";
    }
    
}
