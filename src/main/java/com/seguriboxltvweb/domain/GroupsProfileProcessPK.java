package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Clase entidad de GroupsProfileProcessPK que contiene sus propiedades con sus
 * respectivos métodos GET y SET.
 *
 * @author Germán Hernández López.
 */
@Embeddable
public class GroupsProfileProcessPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupId")
    private short groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProfileProcessId")
    private int profileProcessId;

    public GroupsProfileProcessPK() {
    }

    public GroupsProfileProcessPK(short groupId, int profileProcessId) {
        this.groupId = groupId;
        this.profileProcessId = profileProcessId;
    }

    public short getGroupId() {
        return groupId;
    }

    public void setGroupId(short groupId) {
        this.groupId = groupId;
    }

    public int getProfileProcessId() {
        return profileProcessId;
    }

    public void setProfileProcessId(int profileProcessId) {
        this.profileProcessId = profileProcessId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupId;
        hash += (int) profileProcessId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsProfileProcessPK)) {
            return false;
        }
        GroupsProfileProcessPK other = (GroupsProfileProcessPK) object;
        if (this.groupId != other.groupId) {
            return false;
        }
        if (this.profileProcessId != other.profileProcessId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsProfileProcessPK[ groupId=" + groupId + ", profileProcessId=" + profileProcessId + " ]";
    }

}
