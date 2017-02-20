package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase entidad de GroupsTask que contiene sus propiedades con sus respectivos
 * métodos GET y SET.
 *
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "GroupsTask")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupsTask.findAll", query = "SELECT g FROM GroupsTask g"),
    @NamedQuery(name = "GroupsTask.findByGroupId", query = "SELECT g FROM GroupsTask g WHERE g.groupsTaskPK.groupId = :groupId"),
    @NamedQuery(name = "GroupsTask.findByTaskId", query = "SELECT g FROM GroupsTask g WHERE g.groupsTaskPK.taskId = :taskId")})
public class GroupsTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupsTaskPK groupsTaskPK;

    public GroupsTask() {
    }

    public GroupsTask(GroupsTaskPK groupsTaskPK) {
        this.groupsTaskPK = groupsTaskPK;
    }

    public GroupsTask(short groupId, short taskId) {
        this.groupsTaskPK = new GroupsTaskPK(groupId, taskId);
    }

    public GroupsTaskPK getGroupsTaskPK() {
        return groupsTaskPK;
    }

    public void setGroupsTaskPK(GroupsTaskPK groupsTaskPK) {
        this.groupsTaskPK = groupsTaskPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupsTaskPK != null ? groupsTaskPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsTask)) {
            return false;
        }
        GroupsTask other = (GroupsTask) object;
        if ((this.groupsTaskPK == null && other.groupsTaskPK != null) || (this.groupsTaskPK != null && !this.groupsTaskPK.equals(other.groupsTaskPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsTask[ groupsTaskPK=" + groupsTaskPK + " ]";
    }

}
