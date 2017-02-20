
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de GroupsHSMKey que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "GroupsHSMKey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupsHSMKey.findAll", query = "SELECT g FROM GroupsHSMKey g"),
    @NamedQuery(name = "GroupsHSMKey.findByGroupId", query = "SELECT g FROM GroupsHSMKey g WHERE g.groupsHSMKeyPK.groupId = :groupId"),
    @NamedQuery(name = "GroupsHSMKey.findByHSMKeyId", query = "SELECT g FROM GroupsHSMKey g WHERE g.groupsHSMKeyPK.hSMKeyId = :hSMKeyId")})
public class GroupsHSMKey implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupsHSMKeyPK groupsHSMKeyPK;

    public GroupsHSMKey() {
    }

    public GroupsHSMKey(GroupsHSMKeyPK groupsHSMKeyPK) {
        this.groupsHSMKeyPK = groupsHSMKeyPK;
    }

    public GroupsHSMKey(short groupId, int hSMKeyId) {
        this.groupsHSMKeyPK = new GroupsHSMKeyPK(groupId, hSMKeyId);
    }

    public GroupsHSMKeyPK getGroupsHSMKeyPK() {
        return groupsHSMKeyPK;
    }

    public void setGroupsHSMKeyPK(GroupsHSMKeyPK groupsHSMKeyPK) {
        this.groupsHSMKeyPK = groupsHSMKeyPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupsHSMKeyPK != null ? groupsHSMKeyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsHSMKey)) {
            return false;
        }
        GroupsHSMKey other = (GroupsHSMKey) object;
        if ((this.groupsHSMKeyPK == null && other.groupsHSMKeyPK != null) || (this.groupsHSMKeyPK != null && !this.groupsHSMKeyPK.equals(other.groupsHSMKeyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsHSMKey[ groupsHSMKeyPK=" + groupsHSMKeyPK + " ]";
    }
    
}
