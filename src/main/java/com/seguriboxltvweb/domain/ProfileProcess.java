
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
 *Clase entidad de ProfileProcess que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "ProfileProcess")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProfileProcess.findAll", query = "SELECT p FROM ProfileProcess p"),
    @NamedQuery(name = "ProfileProcess.findByProfileProcessId", query = "SELECT p FROM ProfileProcess p WHERE p.profileProcessId = :profileProcessId"),
    @NamedQuery(name = "ProfileProcess.findByGroupId", query = "SELECT p FROM ProfileProcess p WHERE p.groupId = :groupId"),
    @NamedQuery(name = "ProfileProcess.findByPreconfigured", query = "SELECT p FROM ProfileProcess p WHERE p.preconfigured = :preconfigured"),
    @NamedQuery(name = "ProfileProcess.findByUserId", query = "SELECT p FROM ProfileProcess p WHERE p.userId = :userId"),
    @NamedQuery(name = "ProfileProcess.findByProfileName", query = "SELECT p FROM ProfileProcess p WHERE p.profileName = :profileName"),
    @NamedQuery(name = "ProfileProcess.findByProfileDescription", query = "SELECT p FROM ProfileProcess p WHERE p.profileDescription = :profileDescription"),
    @NamedQuery(name = "ProfileProcess.findBySignatureType", query = "SELECT p FROM ProfileProcess p WHERE p.signatureType = :signatureType"),
    @NamedQuery(name = "ProfileProcess.findByPreviousSignatureType", query = "SELECT p FROM ProfileProcess p WHERE p.previousSignatureType = :previousSignatureType"),
    @NamedQuery(name = "ProfileProcess.findByConversionType", query = "SELECT p FROM ProfileProcess p WHERE p.conversionType = :conversionType"),
    @NamedQuery(name = "ProfileProcess.findByIsActive", query = "SELECT p FROM ProfileProcess p WHERE p.isActive = :isActive"),
    @NamedQuery(name = "ProfileProcess.findByReferenceDate", query = "SELECT p FROM ProfileProcess p WHERE p.referenceDate = :referenceDate"),
    @NamedQuery(name = "ProfileProcess.findByLastUpdated", query = "SELECT p FROM ProfileProcess p WHERE p.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "ProfileProcess.findByHasDocuments", query = "SELECT p FROM ProfileProcess p WHERE p.hasDocuments = :hasDocuments"),
    @NamedQuery(name = "ProfileProcess.findByStorageTime", query = "SELECT p FROM ProfileProcess p WHERE p.storageTime = :storageTime"),
    @NamedQuery(name = "ProfileProcess.findBySourceProfile", query = "SELECT p FROM ProfileProcess p WHERE p.sourceProfile = :sourceProfile"),
    @NamedQuery(name = "ProfileProcess.findByDeactivatedDate", query = "SELECT p FROM ProfileProcess p WHERE p.deactivatedDate = :deactivatedDate"),
    @NamedQuery(name = "ProfileProcess.findByDeactivatedByUserId", query = "SELECT p FROM ProfileProcess p WHERE p.deactivatedByUserId = :deactivatedByUserId")})
public class ProfileProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProfileProcessId")
    private Integer profileProcessId;
    @Column(name = "GroupId")
    private Short groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Preconfigured")
    private boolean preconfigured;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ProfileName")
    private String profileName;
    @Size(max = 500)
    @Column(name = "ProfileDescription")
    private String profileDescription;
    @Column(name = "SignatureType")
    private Short signatureType;
    @Column(name = "PreviousSignatureType")
    private Short previousSignatureType;
    @Column(name = "ConversionType")
    private Short conversionType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Column(name = "LastUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HasDocuments")
    private boolean hasDocuments;
    @Column(name = "StorageTime")
    private Short storageTime;
    @Column(name = "SourceProfile")
    private Integer sourceProfile;
    @Column(name = "DeactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;
    @Column(name = "DeactivatedByUserId")
    private Integer deactivatedByUserId;

    public ProfileProcess() {
    }

    public ProfileProcess(Integer profileProcessId) {
        this.profileProcessId = profileProcessId;
    }

    public ProfileProcess(Integer profileProcessId, boolean preconfigured, int userId, String profileName, boolean isActive, Date referenceDate, boolean hasDocuments) {
        this.profileProcessId = profileProcessId;
        this.preconfigured = preconfigured;
        this.userId = userId;
        this.profileName = profileName;
        this.isActive = isActive;
        this.referenceDate = referenceDate;
        this.hasDocuments = hasDocuments;
    }

    public Integer getProfileProcessId() {
        return profileProcessId;
    }

    public void setProfileProcessId(Integer profileProcessId) {
        this.profileProcessId = profileProcessId;
    }

    public Short getGroupId() {
        return groupId;
    }

    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    public boolean getPreconfigured() {
        return preconfigured;
    }

    public void setPreconfigured(boolean preconfigured) {
        this.preconfigured = preconfigured;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public Short getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(Short signatureType) {
        this.signatureType = signatureType;
    }

    public Short getPreviousSignatureType() {
        return previousSignatureType;
    }

    public void setPreviousSignatureType(Short previousSignatureType) {
        this.previousSignatureType = previousSignatureType;
    }

    public Short getConversionType() {
        return conversionType;
    }

    public void setConversionType(Short conversionType) {
        this.conversionType = conversionType;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean getHasDocuments() {
        return hasDocuments;
    }

    public void setHasDocuments(boolean hasDocuments) {
        this.hasDocuments = hasDocuments;
    }

    public Short getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Short storageTime) {
        this.storageTime = storageTime;
    }

    public Integer getSourceProfile() {
        return sourceProfile;
    }

    public void setSourceProfile(Integer sourceProfile) {
        this.sourceProfile = sourceProfile;
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

    public String SignaturePrevtoString() {

        String str = null;

        switch (previousSignatureType) {

            case 0:
                str = "Ninguna";
                break;
            case 1:
                str = "PDF";
                break;
            case 2:
                str = "CMS";
                break;
            case 3:
                str = "XMLSIG";
                break;
            default:
                str = "Ninguna";
        }
        return str;
    }

    public String ConvertionType2Str() {

        String str = "";

        switch (conversionType) {

            case 0:
                str = "Sin conversión";
                break;
            case 1:
                str = "Office a PDF/A";
                break;
            case 2:
                str = "PDF/A";
                break;
            default:
                str = "Sin conversión";
        }

        return str;
    }

    public String SignatureType2String() {
        String str = "";
        switch (signatureType) {

            case 0:
                str = "Ninguna";
                break;
            case 1:
                str = "PDF";
                break;
            case 2:
                str = "CMS";
                break;
            case 3:
                str = "XMLSIG";
                break;
            default:
                str = "Ninguna";
        }
        return str;
    }

    public String storageTimeStr() {
        String str = "";
      
            str = storageTime+ " " ;

      
        

        return str;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profileProcessId != null ? profileProcessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfileProcess)) {
            return false;
        }
        ProfileProcess other = (ProfileProcess) object;
        if ((this.profileProcessId == null && other.profileProcessId != null) || (this.profileProcessId != null && !this.profileProcessId.equals(other.profileProcessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.ProfileProcess[ profileProcessId=" + profileProcessId + " ]";
    }
    

}
