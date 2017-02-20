package com.seguriboxltvweb.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *Clase entidad de SystemParameters que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@XmlRootElement
public class SystemParameters implements Serializable {

    @Id
    private SystemParameter FILEEXTENSIONS;
    private SystemParameter FILESIZEMAX;
    private SystemParameter EMAILSTORAGEDAYS;
    private SystemParameter SMTPSERVER;
    private SystemParameter SMTPPORT;
    private SystemParameter SMTPANONIMOUS;
    private SystemParameter SMTPUSER;
    private SystemParameter SMTPPASSWORD;
    private SystemParameter SMTPSSLS;
    private SystemParameter DOPKCS11URL;
    private SystemParameter DOPKCS11USERNAME;
    private SystemParameter DOPKCS11PASSWORD;
    private SystemParameter ARCHIVEURL;
    private SystemParameter ARCHIVEUSERNAME;
    private SystemParameter ARCHIVEPASSWORD;
    private SystemParameter SEGURISIGNURLs;
    private SystemParameter SEGURISIGNUSERNAME;
    private SystemParameter SEGURISIGNPASSWORD;
    private SystemParameter SEGURISERVERURL;
    private SystemParameter SEGURISERVERUSERNAME;
    private SystemParameter SEGURISERVERPASSWORD;
    private SystemParameter SEGURIBOXAPIURL;
    private SystemParameter SEGURIBOXAPIUSERNAME;
    private SystemParameter SEGURIBOXAPIPASSWORD;
    private SystemParameter KEYAGENT;
    private SystemParameter ORGANIZATION;
    private SystemParameter STORAGETIME;
    private SystemParameter CRYPTOURL;
    private SystemParameter CRYPTOUSERNAME;
    private SystemParameter CRYPTOPASSWORD;
    private SystemParameter HSMKEYURL;
    private SystemParameter HSMKEYUSERNAME;
    private SystemParameter HSMKEYPASSWORD;
    private SystemParameter SIGNEDALGORITHM;
    private SystemParameter EVENTDAYS;
    private SystemParameter DOPKCS1URL;
    private SystemParameter DOPKCS1USERNAME;
    private SystemParameter DOPKCS1PASSWORD;
    private SystemParameter DOPKCS7URL;
    private SystemParameter DOPKCS7USERNAME;
    private SystemParameter DOPKCS7PASSWORD;
    private SystemParameter FROMEMAIL;
    private SystemParameter ASPOSELIC;
    private SystemParameter EMAILTEMPLATE;
    private SystemParameter FILESIZEZIP;
    private SystemParameter FILESIZEZIPDAYS;
    private SystemParameter URLUSERCERT;

    private boolean anonymous;
    private boolean ssl;

    public SystemParameters() {
        FILEEXTENSIONS = new SystemParameter();
        FILESIZEMAX = new SystemParameter();
        EMAILSTORAGEDAYS = new SystemParameter();
        SMTPSERVER = new SystemParameter();
        SMTPPORT = new SystemParameter();
        SMTPANONIMOUS = new SystemParameter();
        SMTPUSER = new SystemParameter();
        SMTPPASSWORD = new SystemParameter();
        SMTPSSLS = new SystemParameter();
        DOPKCS11URL = new SystemParameter();
        DOPKCS11USERNAME = new SystemParameter();
        DOPKCS11PASSWORD = new SystemParameter();
        ARCHIVEURL = new SystemParameter();
        ARCHIVEUSERNAME = new SystemParameter();
        ARCHIVEPASSWORD = new SystemParameter();
        SEGURISIGNURLs = new SystemParameter();
        SEGURISIGNUSERNAME = new SystemParameter();
        SEGURISIGNPASSWORD = new SystemParameter();
        SEGURISERVERURL = new SystemParameter();
        SEGURISERVERUSERNAME = new SystemParameter();
        SEGURISERVERPASSWORD = new SystemParameter();
        SEGURIBOXAPIURL = new SystemParameter();
        SEGURIBOXAPIUSERNAME = new SystemParameter();
        SEGURIBOXAPIPASSWORD = new SystemParameter();
        KEYAGENT = new SystemParameter();
        ORGANIZATION = new SystemParameter();
        STORAGETIME = new SystemParameter();
        CRYPTOURL = new SystemParameter();
        CRYPTOUSERNAME = new SystemParameter();
        CRYPTOPASSWORD = new SystemParameter();
        HSMKEYURL = new SystemParameter();
        HSMKEYUSERNAME = new SystemParameter();
        HSMKEYPASSWORD = new SystemParameter();
        SIGNEDALGORITHM = new SystemParameter();
        FROMEMAIL = new SystemParameter();
        ASPOSELIC = new SystemParameter();
        EMAILTEMPLATE = new SystemParameter();
        FILESIZEZIP=new SystemParameter();
        FILESIZEZIPDAYS=new SystemParameter();

    }

    public SystemParameter getURLUSERCERT() {
        return URLUSERCERT;
    }

    public void setURLUSERCERT(SystemParameter URLUSERCERT) {
        this.URLUSERCERT = URLUSERCERT;
    }

    
    public SystemParameter getASPOSELIC() {
        return ASPOSELIC;
    }

    public void setASPOSELIC(SystemParameter ASPOSELIC) {
        this.ASPOSELIC = ASPOSELIC;
    }

    public SystemParameter getEMAILTEMPLATE() {
        return EMAILTEMPLATE;
    }

    public void setEMAILTEMPLATE(SystemParameter EMAILTEMPLATE) {
        this.EMAILTEMPLATE = EMAILTEMPLATE;
    }

    public SystemParameter getFILESIZEZIP() {
        return FILESIZEZIP;
    }

    public void setFILESIZEZIP(SystemParameter FILESIZEZIP) {
        this.FILESIZEZIP = FILESIZEZIP;
    }

    public SystemParameter getFILESIZEZIPDAYS() {
        return FILESIZEZIPDAYS;
    }

    public void setFILESIZEZIPDAYS(SystemParameter FILESIZEZIPDAYS) {
        this.FILESIZEZIPDAYS = FILESIZEZIPDAYS;
    }
    
    

    public SystemParameter getFROMEMAIL() {
        return FROMEMAIL;
    }

    public void setFROMEMAIL(SystemParameter FROMEMAIL) {
        this.FROMEMAIL = FROMEMAIL;
    }

    public SystemParameter getEVENTDAYS() {
        return EVENTDAYS;
    }

    public void setEVENTDAYS(SystemParameter EVENTDAYS) {
        this.EVENTDAYS = EVENTDAYS;
    }

    public SystemParameter getDOPKCS1URL() {
        return DOPKCS1URL;
    }

    public void setDOPKCS1URL(SystemParameter DOPKCS1URL) {
        this.DOPKCS1URL = DOPKCS1URL;
    }

    public SystemParameter getDOPKCS1USERNAME() {
        return DOPKCS1USERNAME;
    }

    public void setDOPKCS1USERNAME(SystemParameter DOPKCS1USERNAME) {
        this.DOPKCS1USERNAME = DOPKCS1USERNAME;
    }

    public SystemParameter getDOPKCS1PASSWORD() {
        return DOPKCS1PASSWORD;
    }

    public void setDOPKCS1PASSWORD(SystemParameter DOPKCS1PASSWORD) {
        this.DOPKCS1PASSWORD = DOPKCS1PASSWORD;
    }

    public SystemParameter getDOPKCS7URL() {
        return DOPKCS7URL;
    }

    public void setDOPKCS7URL(SystemParameter DOPKCS7URL) {
        this.DOPKCS7URL = DOPKCS7URL;
    }

    public SystemParameter getDOPKCS7USERNAME() {
        return DOPKCS7USERNAME;
    }

    public void setDOPKCS7USERNAME(SystemParameter DOPKCS7USERNAME) {
        this.DOPKCS7USERNAME = DOPKCS7USERNAME;
    }

    public SystemParameter getDOPKCS7PASSWORD() {
        return DOPKCS7PASSWORD;
    }

    public void setDOPKCS7PASSWORD(SystemParameter DOPKCS7PASSWORD) {
        this.DOPKCS7PASSWORD = DOPKCS7PASSWORD;
    }

    public SystemParameter getSIGNEDALGORITHM() {
        return SIGNEDALGORITHM;
    }

    public void setSIGNEDALGORITHM(SystemParameter SIGNEDALGORITHM) {
        this.SIGNEDALGORITHM = SIGNEDALGORITHM;
    }

//    public void setAnonymous(Boolean anonymous) {
//        if(anonymous) {
//            SMTPANONIMOUS.setParameterValue("true");
//        } else {
//            SMTPANONIMOUS.setParameterValue("false");
//        }
//        this.anonymous = anonymous;
//    }
//
//    public Boolean getSsl() {
//        return SMTPSSLS.getParameterValue().equals("true");
//    }
//
//    public void setSsl(Boolean ssl) {
//        if(ssl) {
//            SMTPSSLS.setParameterValue("true");
//        } else {
//            SMTPSSLS.setParameterValue("false");
//        }
//        this.ssl = ssl;
//    }
    public SystemParameter getFILEEXTENSIONS() {
        return FILEEXTENSIONS;
    }

    public void setFILEEXTENSIONS(SystemParameter FILEEXTENSIONS) {
        this.FILEEXTENSIONS = FILEEXTENSIONS;
    }

    public SystemParameter getFILESIZEMAX() {
        return FILESIZEMAX;
    }

    public void setFILESIZEMAX(SystemParameter FILESIZEMAX) {
        this.FILESIZEMAX = FILESIZEMAX;
    }

    public SystemParameter getEMAILSTORAGEDAYS() {
        return EMAILSTORAGEDAYS;
    }

    public void setEMAILSTORAGEDAYS(SystemParameter EMAILSTORAGEDAYS) {
        this.EMAILSTORAGEDAYS = EMAILSTORAGEDAYS;
    }

    public SystemParameter getSMTPSERVER() {
        return SMTPSERVER;
    }

    public void setSMTPSERVER(SystemParameter SMTPSERVER) {
        this.SMTPSERVER = SMTPSERVER;
    }

    public SystemParameter getSMTPPORT() {
        return SMTPPORT;
    }

    public void setSMTPPORT(SystemParameter SMTPPORT) {
        this.SMTPPORT = SMTPPORT;
    }

    public SystemParameter getSMTPANONIMOUS() {
        return SMTPANONIMOUS;
    }

    public void setSMTPANONIMOUS(SystemParameter SMTPANONIMOUS) {
        this.SMTPANONIMOUS = SMTPANONIMOUS;
    }

    public SystemParameter getSMTPUSER() {
        return SMTPUSER;
    }

    public void setSMTPUSER(SystemParameter SMTPUSER) {
        this.SMTPUSER = SMTPUSER;
    }

    public SystemParameter getSMTPPASSWORD() {
        return SMTPPASSWORD;
    }

    public void setSMTPPASSWORD(SystemParameter SMTPPASSWORD) {
        this.SMTPPASSWORD = SMTPPASSWORD;
    }

    public SystemParameter getSMTPSSLS() {

        return SMTPSSLS;
    }

    public void setSMTPSSLS(SystemParameter SMTPSSLS) {

        this.SMTPSSLS = SMTPSSLS;
    }

    public SystemParameter getDOPKCS11URL() {
        return DOPKCS11URL;
    }

    public void setDOPKCS11URL(SystemParameter DOPKCS11URL) {
        this.DOPKCS11URL = DOPKCS11URL;
    }

    public SystemParameter getDOPKCS11USERNAME() {
        return DOPKCS11USERNAME;
    }

    public void setDOPKCS11USERNAME(SystemParameter DOPKCS11USERNAME) {
        this.DOPKCS11USERNAME = DOPKCS11USERNAME;
    }

    public SystemParameter getDOPKCS11PASSWORD() {
        return DOPKCS11PASSWORD;
    }

    public void setDOPKCS11PASSWORD(SystemParameter DOPKCS11PASSWORD) {
        this.DOPKCS11PASSWORD = DOPKCS11PASSWORD;
    }

    public SystemParameter getARCHIVEURL() {
        return ARCHIVEURL;
    }

    public void setARCHIVEURL(SystemParameter ARCHIVEURL) {
        this.ARCHIVEURL = ARCHIVEURL;
    }

    public SystemParameter getARCHIVEUSERNAME() {
        return ARCHIVEUSERNAME;
    }

    public void setARCHIVEUSERNAME(SystemParameter ARCHIVEUSERNAME) {
        this.ARCHIVEUSERNAME = ARCHIVEUSERNAME;
    }

    public SystemParameter getARCHIVEPASSWORD() {
        return ARCHIVEPASSWORD;
    }

    public void setARCHIVEPASSWORD(SystemParameter ARCHIVEPASSWORD) {
        this.ARCHIVEPASSWORD = ARCHIVEPASSWORD;
    }

    public SystemParameter getSEGURISIGNURLs() {
        return SEGURISIGNURLs;
    }

    public void setSEGURISIGNURLs(SystemParameter SEGURISIGNURLs) {
        this.SEGURISIGNURLs = SEGURISIGNURLs;
    }

    public SystemParameter getSEGURISIGNUSERNAME() {
        return SEGURISIGNUSERNAME;
    }

    public void setSEGURISIGNUSERNAME(SystemParameter SEGURISIGNUSERNAME) {
        this.SEGURISIGNUSERNAME = SEGURISIGNUSERNAME;
    }

    public SystemParameter getSEGURISIGNPASSWORD() {
        return SEGURISIGNPASSWORD;
    }

    public void setSEGURISIGNPASSWORD(SystemParameter SEGURISIGNPASSWORD) {
        this.SEGURISIGNPASSWORD = SEGURISIGNPASSWORD;
    }

    public SystemParameter getSEGURISERVERURL() {
        return SEGURISERVERURL;
    }

    public void setSEGURISERVERURL(SystemParameter SEGURISERVERURL) {
        this.SEGURISERVERURL = SEGURISERVERURL;
    }

    public SystemParameter getSEGURISERVERUSERNAME() {
        return SEGURISERVERUSERNAME;
    }

    public void setSEGURISERVERUSERNAME(SystemParameter SEGURISERVERUSERNAME) {
        this.SEGURISERVERUSERNAME = SEGURISERVERUSERNAME;
    }

    public SystemParameter getSEGURISERVERPASSWORD() {
        return SEGURISERVERPASSWORD;
    }

    public void setSEGURISERVERPASSWORD(SystemParameter SEGURISERVERPASSWORD) {
        this.SEGURISERVERPASSWORD = SEGURISERVERPASSWORD;
    }

    public SystemParameter getSEGURIBOXAPIURL() {
        return SEGURIBOXAPIURL;
    }

    public void setSEGURIBOXAPIURL(SystemParameter SEGURIBOXAPIURL) {
        this.SEGURIBOXAPIURL = SEGURIBOXAPIURL;
    }

    public SystemParameter getSEGURIBOXAPIUSERNAME() {
        return SEGURIBOXAPIUSERNAME;
    }

    public void setSEGURIBOXAPIUSERNAME(SystemParameter SEGURIBOXAPIUSERNAME) {
        this.SEGURIBOXAPIUSERNAME = SEGURIBOXAPIUSERNAME;
    }

    public SystemParameter getSEGURIBOXAPIPASSWORD() {
        return SEGURIBOXAPIPASSWORD;
    }

    public void setSEGURIBOXAPIPASSWORD(SystemParameter SEGURIBOXAPIPASSWORD) {
        this.SEGURIBOXAPIPASSWORD = SEGURIBOXAPIPASSWORD;
    }

    public SystemParameter getKEYAGENT() {
        return KEYAGENT;
    }

    public void setKEYAGENT(SystemParameter KEYAGENT) {
        this.KEYAGENT = KEYAGENT;
    }

    public SystemParameter getORGANIZATION() {
        return ORGANIZATION;
    }

    public void setORGANIZATION(SystemParameter ORGANIZATION) {
        this.ORGANIZATION = ORGANIZATION;
    }

    public SystemParameter getSTORAGETIME() {
        return STORAGETIME;
    }

    public void setSTORAGETIME(SystemParameter STORAGETIME) {
        this.STORAGETIME = STORAGETIME;
    }

    public SystemParameter getCRYPTOURL() {
        return CRYPTOURL;
    }

    public void setCRYPTOURL(SystemParameter CRYPTOURL) {
        this.CRYPTOURL = CRYPTOURL;
    }

    public SystemParameter getCRYPTOUSERNAME() {
        return CRYPTOUSERNAME;
    }

    public void setCRYPTOUSERNAME(SystemParameter CRYPTOUSERNAME) {
        this.CRYPTOUSERNAME = CRYPTOUSERNAME;
    }

    public SystemParameter getCRYPTOPASSWORD() {
        return CRYPTOPASSWORD;
    }

    public void setCRYPTOPASSWORD(SystemParameter CRYPTOPASSWORD) {
        this.CRYPTOPASSWORD = CRYPTOPASSWORD;
    }

    public SystemParameter getHSMKEYURL() {
        return HSMKEYURL;
    }

    public void setHSMKEYURL(SystemParameter HSMKEYURL) {
        this.HSMKEYURL = HSMKEYURL;
    }

    public SystemParameter getHSMKEYUSERNAME() {
        return HSMKEYUSERNAME;
    }

    public void setHSMKEYUSERNAME(SystemParameter HSMKEYUSERNAME) {
        this.HSMKEYUSERNAME = HSMKEYUSERNAME;
    }

    public SystemParameter getHSMKEYPASSWORD() {
        return HSMKEYPASSWORD;
    }

    public void setHSMKEYPASSWORD(SystemParameter HSMKEYPASSWORD) {
        this.HSMKEYPASSWORD = HSMKEYPASSWORD;
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

    public boolean anonymousToBool() {

        boolean bool = SMTPANONIMOUS.getParameterValue().equals("true");

        return bool;
    }

    public boolean sslToBool() {

        boolean bool = SMTPSSLS.getParameterValue().equals("true");

        return bool;
    }

}
