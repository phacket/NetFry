
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de GroupsUser que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "GroupsUser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupsUser.findAll", query = "SELECT g FROM GroupsUser g"),
    @NamedQuery(name = "GroupsUser.findByGroupId", query = "SELECT g FROM GroupsUser g WHERE g.groupsUserPK.groupId = :groupId"),
    @NamedQuery(name = "GroupsUser.findByUserId", query = "SELECT g FROM GroupsUser g WHERE g.groupsUserPK.userId = :userId")})
public class GroupsUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupsUserPK groupsUserPK;

    public GroupsUser() {
    }

    public GroupsUser(GroupsUserPK groupsUserPK) {
        this.groupsUserPK = groupsUserPK;
    }

    public GroupsUser(short groupId, int userId) {
        this.groupsUserPK = new GroupsUserPK(groupId, userId);
    }

    public GroupsUserPK getGroupsUserPK() {
        return groupsUserPK;
    }

    public void setGroupsUserPK(GroupsUserPK groupsUserPK) {
        this.groupsUserPK = groupsUserPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupsUserPK != null ? groupsUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsUser)) {
            return false;
        }
        GroupsUser other = (GroupsUser) object;
        if ((this.groupsUserPK == null && other.groupsUserPK != null) || (this.groupsUserPK != null && !this.groupsUserPK.equals(other.groupsUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsUser[ groupsUserPK=" + groupsUserPK + " ]";
    }
    
}
