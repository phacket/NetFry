
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *Clase CertificateName que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
public class CertificateName implements Serializable {

    @Id
    private String certjson;
    private String certjson2;
    private String certificateName;
    public CertificateName(){}
//    public CertificateName(byte[]certjson2, String firstName, String lastName) {
//       
//        this.certjson2 = certjson2;
//        if(lastName!=null)
//        this.certificateName = firstName + "_" + lastName + ".p12";
//        else
//            this.certificateName = firstName+ ".p12";
//            
//    }

    public String getCertjson2() {
        return certjson2;
    }

    public void setCertjson2(String certjson2) {
        this.certjson2 = certjson2;
    }

    

    
    public String getCertjson() {
        return certjson;
    }

    public void setCertjson(String certjson) {
        this.certjson = certjson;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

}