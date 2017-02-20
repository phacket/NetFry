
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
 *Clase AlgorithmSignSymm que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "AlgorithmSignSymm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlgorithmSignSymm.findAll", query = "SELECT a FROM AlgorithmSignSymm a"),
    @NamedQuery(name = "AlgorithmSignSymm.findByAlgorithmSignId", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.algorithmSignId = :algorithmSignId"),
    @NamedQuery(name = "AlgorithmSignSymm.findByAlgorithmHashId", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.algorithmHashId = :algorithmHashId"),
    @NamedQuery(name = "AlgorithmSignSymm.findByAlgorithmAsymmetricId", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.algorithmAsymmetricId = :algorithmAsymmetricId"),
    @NamedQuery(name = "AlgorithmSignSymm.findByReferenceDate", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.referenceDate = :referenceDate"),
    @NamedQuery(name = "AlgorithmSignSymm.findByExpirationDate", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.expirationDate = :expirationDate"),
    @NamedQuery(name = "AlgorithmSignSymm.findByAlgorithmName", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.algorithmName = :algorithmName"),
    @NamedQuery(name = "AlgorithmSignSymm.findByOid", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.oid = :oid"),
    @NamedQuery(name = "AlgorithmSignSymm.findByIsActive", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.isActive = :isActive"),
    @NamedQuery(name = "AlgorithmSignSymm.findByBinOid", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.binOid = :binOid"),
    @NamedQuery(name = "AlgorithmSignSymm.findByHashBin", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.hashBin = :hashBin"),
    @NamedQuery(name = "AlgorithmSignSymm.findByAsymmBin", query = "SELECT a FROM AlgorithmSignSymm a WHERE a.asymmBin = :asymmBin")})
public class AlgorithmSignSymm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AlgorithmSignId")
    private Integer algorithmSignId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AlgorithmHashId")
    private int algorithmHashId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AlgorithmAsymmetricId")
    private int algorithmAsymmetricId;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;
    @Size(max = 50)
    @Column(name = "BinOid")
    private String binOid;
    @Size(max = 50)
    @Column(name = "HashBin")
    private String hashBin;
    @Size(max = 50)
    @Column(name = "AsymmBin")
    private String asymmBin;
    private String algorithmSignIdString;
    
     @Size(max = 50)
    @Column(name = "AsymetricName")
    private String asymetricName;
    @Size(max = 50)
    @Column(name = "HashName")
    private String hashName;

    public String getAsymetricName() {
        return asymetricName;
    }

    public void setAsymetricName(String asymetricName) {
        this.asymetricName = asymetricName;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }
    
    
    public AlgorithmSignSymm() {
    }

    public AlgorithmSignSymm(Integer algorithmSignId) {
        this.algorithmSignId = algorithmSignId;
    }

    public String getAlgorithmSignIdString() {
        algorithmSignIdString = Integer.toString(getAlgorithmSignId());
        return algorithmSignIdString;
    }

    public void setAlgorithmSignIdString(String algorithmSignIdString) {
        this.algorithmSignIdString = algorithmSignIdString;
    }

    public AlgorithmSignSymm(Integer algorithmSignId, int algorithmHashId, int algorithmAsymmetricId, Date referenceDate, boolean isActive) {
        this.algorithmSignId = algorithmSignId;
        this.algorithmHashId = algorithmHashId;
        this.algorithmAsymmetricId = algorithmAsymmetricId;
        this.referenceDate = referenceDate;
        this.isActive = isActive;
    }

    public Integer getAlgorithmSignId() {
        return algorithmSignId;
    }

    public void setAlgorithmSignId(Integer algorithmSignId) {
        this.algorithmSignId = algorithmSignId;
    }

    public int getAlgorithmHashId() {
        return algorithmHashId;
    }

    public void setAlgorithmHashId(int algorithmHashId) {
        this.algorithmHashId = algorithmHashId;
    }

    public int getAlgorithmAsymmetricId() {
        return algorithmAsymmetricId;
    }

    public void setAlgorithmAsymmetricId(int algorithmAsymmetricId) {
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

    public String getHashBin() {
        return hashBin;
    }

    public void setHashBin(String hashBin) {
        this.hashBin = hashBin;
    }

    public String getAsymmBin() {
        return asymmBin;
    }

    public void setAsymmBin(String asymmBin) {
        this.asymmBin = asymmBin;
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
        hash += (algorithmSignId != null ? algorithmSignId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlgorithmSignSymm)) {
            return false;
        }
        AlgorithmSignSymm other = (AlgorithmSignSymm) object;
        if ((this.algorithmSignId == null && other.algorithmSignId != null) || (this.algorithmSignId != null && !this.algorithmSignId.equals(other.algorithmSignId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AlgorithmSignSymm{" + "algorithmSignId=" + algorithmSignId + ", algorithmHashId=" + algorithmHashId + ", algorithmAsymmetricId=" + algorithmAsymmetricId + ", referenceDate=" + referenceDate + ", expirationDate=" + expirationDate + ", algorithmName=" + algorithmName + ", oid=" + oid + ", isActive=" + isActive + ", binOid=" + binOid + ", hashBin=" + hashBin + ", asymmBin=" + asymmBin + ", algorithmSignIdString=" + algorithmSignIdString + '}';
    }

  
}
