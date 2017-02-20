package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase entidad de GroupsProfileProcess que contiene sus propiedades con sus
 * respectivos métodos GET y SET.
 *
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "GroupsProfileProcess")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupsProfileProcess.findAll", query = "SELECT g FROM GroupsProfileProcess g"),
    @NamedQuery(name = "GroupsProfileProcess.findByGroupId", query = "SELECT g FROM GroupsProfileProcess g WHERE g.groupsProfileProcessPK.groupId = :groupId"),
    @NamedQuery(name = "GroupsProfileProcess.findByProfileProcessId", query = "SELECT g FROM GroupsProfileProcess g WHERE g.groupsProfileProcessPK.profileProcessId = :profileProcessId")})
public class GroupsProfileProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupsProfileProcessPK groupsProfileProcessPK;

    public GroupsProfileProcess() {
    }

    public GroupsProfileProcess(GroupsProfileProcessPK groupsProfileProcessPK) {
        this.groupsProfileProcessPK = groupsProfileProcessPK;
    }

    public GroupsProfileProcess(short groupId, int profileProcessId) {
        this.groupsProfileProcessPK = new GroupsProfileProcessPK(groupId, profileProcessId);
    }

    public GroupsProfileProcessPK getGroupsProfileProcessPK() {
        return groupsProfileProcessPK;
    }

    public void setGroupsProfileProcessPK(GroupsProfileProcessPK groupsProfileProcessPK) {
        this.groupsProfileProcessPK = groupsProfileProcessPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupsProfileProcessPK != null ? groupsProfileProcessPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsProfileProcess)) {
            return false;
        }
        GroupsProfileProcess other = (GroupsProfileProcess) object;
        if ((this.groupsProfileProcessPK == null && other.groupsProfileProcessPK != null) || (this.groupsProfileProcessPK != null && !this.groupsProfileProcessPK.equals(other.groupsProfileProcessPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsProfileProcess[ groupsProfileProcessPK=" + groupsProfileProcessPK + " ]";
    }

}
