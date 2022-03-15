/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author r3nb0
 */
@Entity
@Table(name = "AppLog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppLog.findAll", query = "SELECT a FROM AppLog a"),
    @NamedQuery(name = "AppLog.findById", query = "SELECT a FROM AppLog a WHERE a.id = :id"),
    @NamedQuery(name = "AppLog.findByDescription", query = "SELECT a FROM AppLog a WHERE a.description = :description"),
    @NamedQuery(name = "AppLog.findByTime", query = "SELECT a FROM AppLog a WHERE a.time = :time"),
    @NamedQuery(name = "AppLog.findByLocation", query = "SELECT a FROM AppLog a WHERE a.location = :location"),
    @NamedQuery(name = "AppLog.findByRemoved", query = "SELECT a FROM AppLog a WHERE a.removed = :removed")})
public class AppLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "time")
    private String time;
    @Basic(optional = false)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Column(name = "removed")
    private String removed;

    public AppLog() {
    }

    public AppLog(Integer id) {
        this.id = id;
    }

    public AppLog(Integer id, String description, String time, String location, String removed) {
        this.id = id;
        this.description = description;
        this.time = time;
        this.location = location;
        this.removed = removed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppLog)) {
            return false;
        }
        AppLog other = (AppLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AppLog[ id=" + id + " ]";
    }
    
}
