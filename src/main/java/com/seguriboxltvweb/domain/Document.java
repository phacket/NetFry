/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguriboxltvweb.domain;

import com.seguriboxltvweb.jsf.util.Util;
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
 *Clase entidad de Document que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "Document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByDocumentId", query = "SELECT d FROM Document d WHERE d.documentId = :documentId"),
    @NamedQuery(name = "Document.findByProfileProcessId", query = "SELECT d FROM Document d WHERE d.profileProcessId = :profileProcessId"),
    @NamedQuery(name = "Document.findByAttachmentName", query = "SELECT d FROM Document d WHERE d.attachmentName = :attachmentName"),
    @NamedQuery(name = "Document.findByCreatedByUser", query = "SELECT d FROM Document d WHERE d.createdByUserId = :createdByUserId"),
    @NamedQuery(name = "Document.findByDocumentName", query = "SELECT d FROM Document d WHERE d.documentName = :documentName"),
    @NamedQuery(name = "Document.findByDateCreated", query = "SELECT d FROM Document d WHERE d.dateCreated = :dateCreated"),
    @NamedQuery(name = "Document.findByExpirationDate", query = "SELECT d FROM Document d WHERE d.expirationDate = :expirationDate"),
    @NamedQuery(name = "Document.findByMimeType", query = "SELECT d FROM Document d WHERE d.mimeType = :mimeType"),
    @NamedQuery(name = "Document.findByFileName", query = "SELECT d FROM Document d WHERE d.fileName = :fileName"),
    @NamedQuery(name = "Document.findBySignatureType", query = "SELECT d FROM Document d WHERE d.signatureType = :signatureType"),
    @NamedQuery(name = "Document.findByIsActive", query = "SELECT d FROM Document d WHERE d.isActive = :isActive")})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DocumentId")
    private Integer documentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProfileProcessId")
    private int profileProcessId;

    @Size(max = 50)
    @Column(name = "AttachmentName")
    private String attachmentName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreatedByUser")
    private String createdByUserId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DocumentName")
    private String documentName;
    @Column(name = "DateCreated")
    private String dateCreatedString;
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ExpirationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Size(max = 100)
    @Column(name = "MimeType")
    private String mimeType;
    @Size(max = 250)
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "SignatureType")
    private String signatureType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;

    public Document() {
    }

    public Document(Integer documentId) {
        this.documentId = documentId;
    }

    public Document(Integer documentId, int profileProcessId, String createdByUserId, String documentName, Date expirationDate, boolean isActive) {
        this.documentId = documentId;
        this.profileProcessId = profileProcessId;
        this.createdByUserId = createdByUserId;
        this.documentName = documentName;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }

    public String getDateCreatedString() {
        if(dateCreated==null){
        
            dateCreatedString="";
        }else{
            
            dateCreatedString=Util.dateTooUtc(dateCreated);
        
        }
        
        return dateCreatedString;
    }
    
    

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public int getProfileProcessId() {
        return profileProcessId;
    }

    public void setProfileProcessId(int profileProcessId) {
        this.profileProcessId = profileProcessId;
    }


    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getCreatedByUser() {
        return createdByUserId;
    }

    public void setCreatedByUser(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentId != null ? documentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.Document[ documentId=" + documentId + " ]";
    }
    
}
