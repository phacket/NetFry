
package com.seguriboxltvweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Clase entidad de Task que contiene sus propiedades con sus respectivos métodos GET y SET.
 * @author Germán Hernández López.
 */
@Entity
@Table(name = "Task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByTaskId", query = "SELECT t FROM Task t WHERE t.taskId = :taskId"),
    @NamedQuery(name = "Task.findByTaskCode", query = "SELECT t FROM Task t WHERE t.taskCode = :taskCode"),
    @NamedQuery(name = "Task.findByMenu", query = "SELECT t FROM Task t WHERE t.menu = :menu"),
    @NamedQuery(name = "Task.findByTaskName", query = "SELECT t FROM Task t WHERE t.taskName = :taskName"),
    @NamedQuery(name = "Task.findByPortalAdmin", query = "SELECT t FROM Task t WHERE t.portalAdmin = :portalAdmin"),
    @NamedQuery(name = "Task.findByPortalUser", query = "SELECT t FROM Task t WHERE t.portalUser = :portalUser")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TaskId")
    private Short taskId;
    @Size(max = 50)
    @Column(name = "TaskCode")
    private String taskCode;
    @Size(max = 50)
    @Column(name = "Menu")
    private String menu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TaskName")
    private String taskName;
    @Column(name = "PortalAdmin")
    private Boolean portalAdmin;
    @Column(name = "PortalUser")
    private Boolean portalUser;

    public Task() {
    }

    public Task(Short taskId) {
        this.taskId = taskId;
    }

    public Task(Short taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public Short getTaskId() {
        return taskId;
    }

    public void setTaskId(Short taskId) {
        this.taskId = taskId;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Boolean getPortalAdmin() {
        return portalAdmin;
    }

    public void setPortalAdmin(Boolean portalAdmin) {
        this.portalAdmin = portalAdmin;
    }

    public Boolean getPortalUser() {
        return portalUser;
    }

    public void setPortalUser(Boolean portalUser) {
        this.portalUser = portalUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguriboxltvweb.norelations.Task[ taskId=" + taskId + " ]";
    }
    
}
