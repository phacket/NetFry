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
 *Clase entidad de usuarios que contiene todas las propiedades con sus respectivos métodos GET y SET.
 * @author MiguelADM
 */
@Entity
@Table(name = "Users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserType", query = "SELECT u FROM Users u WHERE u.userType = :userType"),
    @NamedQuery(name = "Users.findByOnlyReader", query = "SELECT u FROM Users u WHERE u.onlyReader = :onlyReader"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "Users.findByMiddleName", query = "SELECT u FROM Users u WHERE u.middleName = :middleName"),
    @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "Users.findByOrganization", query = "SELECT u FROM Users u WHERE u.organization = :organization"),
    @NamedQuery(name = "Users.findByKeySizeBits", query = "SELECT u FROM Users u WHERE u.keySizeBits = :keySizeBits"),
    @NamedQuery(name = "Users.findByChallengePrase", query = "SELECT u FROM Users u WHERE u.challengePrase = :challengePrase"),
    @NamedQuery(name = "Users.findByPrivateKey", query = "SELECT u FROM Users u WHERE u.privateKey = :privateKey"),
    @NamedQuery(name = "Users.findByRfc", query = "SELECT u FROM Users u WHERE u.rfc = :rfc"),
    @NamedQuery(name = "Users.findByCurp", query = "SELECT u FROM Users u WHERE u.curp = :curp"),
    @NamedQuery(name = "Users.findByPuesto", query = "SELECT u FROM Users u WHERE u.puesto = :puesto"),
    @NamedQuery(name = "Users.findByIdentificador", query = "SELECT u FROM Users u WHERE u.identificador = :identificador"),
    @NamedQuery(name = "Users.findByCalle", query = "SELECT u FROM Users u WHERE u.calle = :calle"),
    @NamedQuery(name = "Users.findByNumero", query = "SELECT u FROM Users u WHERE u.numero = :numero"),
    @NamedQuery(name = "Users.findByCodigoPostal", query = "SELECT u FROM Users u WHERE u.codigoPostal = :codigoPostal"),
    @NamedQuery(name = "Users.findByMunicipio", query = "SELECT u FROM Users u WHERE u.municipio = :municipio"),
    @NamedQuery(name = "Users.findByColonia", query = "SELECT u FROM Users u WHERE u.colonia = :colonia"),
    @NamedQuery(name = "Users.findByEstado", query = "SELECT u FROM Users u WHERE u.estado = :estado"),
    @NamedQuery(name = "Users.findByAuthenticationMode", query = "SELECT u FROM Users u WHERE u.authenticationMode = :authenticationMode"),
    @NamedQuery(name = "Users.findByNoSerie", query = "SELECT u FROM Users u WHERE u.noSerie = :noSerie"),
    @NamedQuery(name = "Users.findByCertVencimiento", query = "SELECT u FROM Users u WHERE u.certVencimiento = :certVencimiento"),
    @NamedQuery(name = "Users.findByHash256Certificate", query = "SELECT u FROM Users u WHERE u.hash256Certificate = :hash256Certificate"),
    @NamedQuery(name = "Users.findByIsActive", query = "SELECT u FROM Users u WHERE u.isActive = :isActive"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByTelNumber", query = "SELECT u FROM Users u WHERE u.telNumber = :telNumber"),
    @NamedQuery(name = "Users.findByCountryCode", query = "SELECT u FROM Users u WHERE u.countryCode = :countryCode"),
    @NamedQuery(name = "Users.findByIsLockedOut", query = "SELECT u FROM Users u WHERE u.isLockedOut = :isLockedOut"),
    @NamedQuery(name = "Users.findByLastSigninDate", query = "SELECT u FROM Users u WHERE u.lastSigninDate = :lastSigninDate"),
    @NamedQuery(name = "Users.findByReferenceDate", query = "SELECT u FROM Users u WHERE u.referenceDate = :referenceDate"),
    @NamedQuery(name = "Users.findByLastUpdated", query = "SELECT u FROM Users u WHERE u.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "Users.findByIPAddress", query = "SELECT u FROM Users u WHERE u.iPAddress = :iPAddress"),
    @NamedQuery(name = "Users.findByStatusId", query = "SELECT u FROM Users u WHERE u.statusId = :statusId"),
    @NamedQuery(name = "Users.findByLastUserLogRecordId", query = "SELECT u FROM Users u WHERE u.lastUserLogRecordId = :lastUserLogRecordId"),
    @NamedQuery(name = "Users.findByLastActivityDate", query = "SELECT u FROM Users u WHERE u.lastActivityDate = :lastActivityDate"),
    @NamedQuery(name = "Users.findBySessionId", query = "SELECT u FROM Users u WHERE u.sessionId = :sessionId")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserType")
    private short userType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AreaId")
    private short areaId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OnlyReader")
    private boolean onlyReader;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Username")
    private String username;
//    @Lob
//    @Column(name = "Password")
//    private byte[] password;
    @NotNull
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MiddleName")
    private String middleName;
    @Size(max = 50)
    @Column(name = "LastName")
    private String lastName;
    @Size(max = 50)
    @Column(name = "Organization")
    private String organization;

    @Column(name = "KeySizeBits")
    private Short keySizeBits;
    @Size(max = 50)
    @Column(name = "ChallengePrase")
    private String challengePrase;
    @Size(max = 50)
    @Column(name = "PrivateKey")
    private String privateKey;
    @Size(max = 15)
    @Column(name = "RFC")
    private String rfc;
    @Size(max = 25)
    @Column(name = "CURP")
    private String curp;
    @Size(max = 50)
    @Column(name = "Puesto")
    private String puesto;
    @Size(max = 50)
    @Column(name = "Identificador")
    private String identificador;
    @Size(max = 50)
    @Column(name = "Calle")
    private String calle;
    @Size(max = 10)
    @Column(name = "Numero")
    private String numero;
    //@Size(max = 5)
    @Column(name = "CodigoPostal")
    private String codigoPostal;
    @Size(max = 50)
    @Column(name = "Municipio")
    private String municipio;
    @Size(max = 50)
    @Column(name = "Colonia")
    private String colonia;
    @Size(max = 50)
    @Column(name = "Estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AuthenticationMode")
    private short authenticationMode;
    @Lob
    @Column(name = "Certificate")
    private byte[] certificate;
    @Size(max = 100)
    @Column(name = "NoSerie")
    private String noSerie;
    @Column(name = "CertVencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date certVencimiento;
    @Size(max = 500)
    @Column(name = "Hash256Certificate")
    private String hash256Certificate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive")
    private boolean isActive;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 250)
    @Column(name = "Email")
    private String email;
    @Size(max = 20)
    @Column(name = "TelNumber")
    private String telNumber;
    @Size(max = 2)
    @Column(name = "CountryCode")
    private String countryCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsLockedOut")
    private boolean isLockedOut;
    @Column(name = "LastSigninDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSigninDate;
    @Column(name = "ReferenceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date referenceDate;
    @Column(name = "LastUpdated")
    @Temporal(TemporalType.DATE)
    private Date lastUpdated;
    @Size(max = 40)
    @Column(name = "IPAddress")
    private String iPAddress;
    @Column(name = "StatusId")
    private Short statusId;
    @Column(name = "LastUserLogRecordId")
    private Integer lastUserLogRecordId;
    @Column(name = "LastActivityDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivityDate;
    @Size(max = 250)
    @Column(name = "SessionId")
    private String sessionId;
    private String certB64;
    private String p12;

    @Size(max = 50)
    @Column(name = "AreaName")
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getP12() {
        return p12;
    }

    public void setP12(String p12) {
        this.p12 = p12;
    }

    public String getCertB64() {
        return certB64;
    }

    public void setCertB64(String certB64) {
        this.certB64 = certB64;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getiPAddress() {
        return iPAddress;
    }

    public void setiPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, short userType, boolean onlyReader, String username, String firstName, String middleName, short authenticationMode, boolean isActive, boolean isLockedOut) {
        this.userId = userId;
        this.userType = userType;
        this.onlyReader = onlyReader;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.authenticationMode = authenticationMode;
        this.isActive = isActive;
        this.isLockedOut = isLockedOut;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public short getUserType() {
        return userType;
    }

    public void setUserType(short userType) {
        this.userType = userType;
    }

    public boolean getOnlyReader() {
        return onlyReader;
    }

    public void setOnlyReader(boolean onlyReader) {
        this.onlyReader = onlyReader;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public short getAreaId() {
        return areaId;
    }

    public void setAreaId(short areaId) {
        this.areaId = areaId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Short getKeySizeBits() {
        return keySizeBits;
    }

    public void setKeySizeBits(Short keySizeBits) {
        this.keySizeBits = keySizeBits;
    }

    public String getChallengePrase() {
        return challengePrase;
    }

    public void setChallengePrase(String challengePrase) {
        this.challengePrase = challengePrase;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public short getAuthenticationMode() {
        return authenticationMode;
    }

    public void setAuthenticationMode(short authenticationMode) {
        this.authenticationMode = authenticationMode;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    public Date getCertVencimiento() {
        return certVencimiento;
    }

    public void setCertVencimiento(Date certVencimiento) {
        this.certVencimiento = certVencimiento;
    }

    public String getHash256Certificate() {
        return hash256Certificate;
    }

    public void setHash256Certificate(String hash256Certificate) {
        this.hash256Certificate = hash256Certificate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public boolean getIsLockedOut() {
        return isLockedOut;
    }

    public void setIsLockedOut(boolean isLockedOut) {
        this.isLockedOut = isLockedOut;
    }

    public Date getLastSigninDate() {
        return lastSigninDate;
    }

    public void setLastSigninDate(Date lastSigninDate) {
        this.lastSigninDate = lastSigninDate;
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

    public String getIPAddress() {
        return iPAddress;
    }

    public void setIPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public Short getStatusId() {
        return statusId;
    }

    public void setStatusId(Short statusId) {
        this.statusId = statusId;
    }

    public Integer getLastUserLogRecordId() {
        return lastUserLogRecordId;
    }

    public void setLastUserLogRecordId(Integer lastUserLogRecordId) {
        this.lastUserLogRecordId = lastUserLogRecordId;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String FullName() {

        if (firstName == null) {

            firstName = "";
        }

        if (middleName == null) {

            middleName = "";
        }

        if (lastName == null) {

            lastName = "";
        }

        return firstName + " " + middleName + " " + lastName;
    }

    public String authModeStr() {
        String str = "";

        if (authenticationMode == 1) {

            str = "Usuario y contraseña";
        } else {
            if (authenticationMode == 2) {

                str = "Acceso seguro";
            } else {

                str = "";
            }
        }

        return str;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Users{" + "userId=" + userId + ", userType=" + userType + ", areaId=" + areaId + ", onlyReader=" + onlyReader + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", organization=" + organization + ", keySizeBits=" + keySizeBits + ", challengePrase=" + challengePrase + ", privateKey=" + privateKey + ", rfc=" + rfc + ", curp=" + curp + ", puesto=" + puesto + ", identificador=" + identificador + ", calle=" + calle + ", numero=" + numero + ", codigoPostal=" + codigoPostal + ", municipio=" + municipio + ", colonia=" + colonia + ", estado=" + estado + ", authenticationMode=" + authenticationMode + ", certificate=" + certificate + ", noSerie=" + noSerie + ", certVencimiento=" + certVencimiento + ", hash256Certificate=" + hash256Certificate + ", isActive=" + isActive + ", email=" + email + ", telNumber=" + telNumber + ", countryCode=" + countryCode + ", isLockedOut=" + isLockedOut + ", lastSigninDate=" + lastSigninDate + ", referenceDate=" + referenceDate + ", lastUpdated=" + lastUpdated + ", iPAddress=" + iPAddress + ", statusId=" + statusId + ", lastUserLogRecordId=" + lastUserLogRecordId + ", lastActivityDate=" + lastActivityDate + ", sessionId=" + sessionId + '}';
    }

}
