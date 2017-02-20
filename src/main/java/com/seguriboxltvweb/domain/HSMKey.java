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
 *Clase entidad de HSMKey que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "HSMKey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HSMKey.findAll", query = "SELECT h FROM HSMKey h"),
    @NamedQuery(name = "HSMKey.findByHSMKeyId", query = "SELECT h FROM HSMKey h WHERE h.hSMKeyId = :hSMKeyId"),
    @NamedQuery(name = "HSMKey.findByAlgorithmSignId", query = "SELECT h FROM HSMKey h WHERE h.algorithmSignId = :algorithmSignId"),
    @NamedQuery(name = "HSMKey.findByKeyTag", query = "SELECT h FROM HSMKey h WHERE h.keyTag = :keyTag"),
    @NamedQuery(name = "HSMKey.findByKeySize", query = "SELECT h FROM HSMKey h WHERE h.keySize = :keySize"),
    @NamedQuery(name = "HSMKey.findByOid", query = "SELECT h FROM HSMKey h WHERE h.oid = :oid"),
    @NamedQuery(name = "HSMKey.findByReferenceDate", query = "SELECT h FROM HSMKey h WHERE h.referenceDate = :referenceDate"),
    @NamedQuery(name = "HSMKey.findByIsActive", query = "SELECT h FROM HSMKey h WHERE h.isActive = :isActive"),
    @NamedQuery(name = "HSMKey.findByCertificateExpiration", query = "SELECT h FROM HSMKey h WHERE h.certificateExpiration = :certificateExpiration")})
public class HSMKey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HSMKeyId")
    private Integer hSMKeyId;
    @Column(name = "AlgorithmSignId")
    private Integer algorithmSignId;
    @Size(max = 150)
    @Column(name = "KeyTag")
    private String keyTag;
    @Column(name = "KeySize")
    private Integer keySize;
    @Size(max = 50)
    @Column(name = "Oid")
    private String oid;
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;
    @Lob
    @Column(name = "Certificate")
    private byte[] certificate;
    @Column(name = "CertificateExpiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date certificateExpiration;
    private String HSMKeyIdString;

    public HSMKey() {
    }

    public HSMKey(Integer hSMKeyId) {
        this.hSMKeyId = hSMKeyId;
    }

    public HSMKey(Integer hSMKeyId, boolean isActive) {
        this.hSMKeyId = hSMKeyId;
        this.isActive = isActive;
    }

    public String getHSMKeyIdString() {
        HSMKeyIdString = getHSMKeyId().toString();
        return HSMKeyIdString;
    }

    public void setHSMKeyIdString(String HSMKeyIdString) {
        this.HSMKeyIdString = HSMKeyIdString;
    }

    public Integer getHSMKeyId() {
        return hSMKeyId;
    }

    public void setHSMKeyId(Integer hSMKeyId) {
        this.hSMKeyId = hSMKeyId;
    }

    public Integer getAlgorithmSignId() {
        return algorithmSignId;
    }

    public void setAlgorithmSignId(Integer algorithmSignId) {
        this.algorithmSignId = algorithmSignId;
    }

    public String getKeyTag() {
        return keyTag;
    }

    public void setKeyTag(String keyTag) {
        this.keyTag = keyTag;
    }

    public Integer getKeySize() {
        return keySize;
    }

    public void setKeySize(Integer keySize) {
        this.keySize = keySize;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public Date getCertificateExpiration() {
        return certificateExpiration;
    }

    public void setCertificateExpiration(Date certificateExpiration) {
        this.certificateExpiration = certificateExpiration;
    }

    public String StatusStr() {
        String str = null;
        if (isActive == true) {
            str = "Activo";
        }else
        {
        
            str="Desactivado";
        }
        
        return str;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hSMKeyId != null ? hSMKeyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HSMKey)) {
            return false;
        }
        HSMKey other = (HSMKey) object;
        if ((this.hSMKeyId == null && other.hSMKeyId != null) || (this.hSMKeyId != null && !this.hSMKeyId.equals(other.hSMKeyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HSMKey{" + "hSMKeyId=" + hSMKeyId + ", algorithmSignId=" + algorithmSignId + ", keyTag=" + keyTag + ", keySize=" + keySize + ", oid=" + oid + ", referenceDate=" + referenceDate + ", isActive=" + isActive + ", certificate=" + certificate + ", certificateExpiration=" + certificateExpiration + '}';
    }

}
