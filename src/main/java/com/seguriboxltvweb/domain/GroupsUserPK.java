
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *Clase entidad de GroupsUserPK que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Embeddable
public class GroupsUserPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupId")
    private short groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId")
    private int userId;

    public GroupsUserPK() {
    }

    public GroupsUserPK(short groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public short getGroupId() {
        return groupId;
    }

    public void setGroupId(short groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupId;
        hash += (int) userId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsUserPK)) {
            return false;
        }
        GroupsUserPK other = (GroupsUserPK) object;
        if (this.groupId != other.groupId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsUserPK[ groupId=" + groupId + ", userId=" + userId + " ]";
    }
    
}
