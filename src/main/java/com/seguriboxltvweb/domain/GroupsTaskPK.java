
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *Clase entidad de GroupsTaskPK que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Embeddable
public class GroupsTaskPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "GroupId")
    private short groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TaskId")
    private short taskId;

    public GroupsTaskPK() {
    }

    public GroupsTaskPK(short groupId, short taskId) {
        this.groupId = groupId;
        this.taskId = taskId;
    }

    public short getGroupId() {
        return groupId;
    }

    public void setGroupId(short groupId) {
        this.groupId = groupId;
    }

    public short getTaskId() {
        return taskId;
    }

    public void setTaskId(short taskId) {
        this.taskId = taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) groupId;
        hash += (int) taskId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsTaskPK)) {
            return false;
        }
        GroupsTaskPK other = (GroupsTaskPK) object;
        if (this.groupId != other.groupId) {
            return false;
        }
        if (this.taskId != other.taskId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.GroupsTaskPK[ groupId=" + groupId + ", taskId=" + taskId + " ]";
    }
    
}
