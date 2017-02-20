package com.seguriboxltvweb.domain;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MiguelADM
 */
@Entity
@XmlRootElement
public class SeguriServer implements Serializable {

    @Id
    private String pkcs10b64;
    private String DnCn;
    private String DnO;
    private String DnC;
    private String DnE;
    private String DnL;
    private String DnS;
    private String DnOu;
    private String ValidFrom;
    private String ValidTo;

    public String getPkcs10b64() {
        return pkcs10b64;
    }

    public void setPkcs10b64(String pkcs10b64) {
        this.pkcs10b64 = pkcs10b64;
    }

    public String getDnCn() {
        return DnCn;
    }

    public void setDnCn(String DnCn) {
        this.DnCn = DnCn;
    }

    public String getDnO() {
        return DnO;
    }

    public void setDnO(String DnO) {
        this.DnO = DnO;
    }

    public String getDnC() {
        return DnC;
    }

    public void setDnC(String DnC) {
        this.DnC = DnC;
    }

    public String getDnE() {
        return DnE;
    }

    public void setDnE(String DnE) {
        this.DnE = DnE;
    }

    public String getDnL() {
        return DnL;
    }

    public void setDnL(String DnL) {
        this.DnL = DnL;
    }

    public String getDnS() {
        return DnS;
    }

    public void setDnS(String DnS) {
        this.DnS = DnS;
    }

    public String getDnOu() {
        return DnOu;
    }

    public void setDnOu(String DnOu) {
        this.DnOu = DnOu;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String ValidFrom) {
        this.ValidFrom = ValidFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String ValidTo) {
        this.ValidTo = ValidTo;
    }
                                                    
    
}