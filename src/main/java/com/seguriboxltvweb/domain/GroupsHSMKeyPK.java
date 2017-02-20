
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *Clase entidad de GroupsHSMKeyPK que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Embeddable
public class GroupsHSMKeyPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupId")
    private short groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HSMKeyId")
    private int hSMKeyId;

    public GroupsHSMKeyPK() {
    }

    public GroupsHSMKeyPK(short groupId, int hSMKeyId) {
        this.groupId = groupId;
        this.hSMKeyId = hSMKeyId;
    }

    public short getGroupId() {
        return groupId;
    }

    public void setGroupId(short groupId) {
        this.groupId = groupId;
    }

    public int getHSMKeyId() {
        return hSMKeyId;
    }

    public void setHSMKeyId(int hSMKeyId) {
        this.hSMKeyId = hSMKeyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupId;
        hash += (int) hSMKeyId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsHSMKeyPK)) {
            return false;
        }
        GroupsHSMKeyPK other = (GroupsHSMKeyPK) object;
        if (this.groupId != other.groupId) {
            return false;
        }
        if (this.hSMKeyId != other.hSMKeyId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsHSMKeyPK[ groupId=" + groupId + ", hSMKeyId=" + hSMKeyId + " ]";
    }
    
}
