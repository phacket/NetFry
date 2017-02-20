
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Id;

/**
 *Clase entidad de userSatate que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
public class UserState implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    protected int UserId;
    protected String UserName;
    protected short StatusId;
    protected String FirstName;
    protected String MiddleName;
    protected String LastName;
    protected short AuthenticationMode;
    protected short UserType;
    protected String IPAddress;

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public short getStatusId() {
        return StatusId;
    }

    public void setStatusId(short StatusId) {
        this.StatusId = StatusId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public short getAuthenticationMode() {
        return AuthenticationMode;
    }

    public void setAuthenticationMode(short AuthenticationMode) {
        this.AuthenticationMode = AuthenticationMode;
    }

    public short getUserType() {
        return UserType;
    }

    public void setUserType(short UserType) {
        this.UserType = UserType;
    }
    @Override
     public String toString() {
        return this.FirstName + " " + this.MiddleName + " " + this.LastName;
    }
}