/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author r3nb0
 */
@Entity
@Table(name = "PaymentGateway")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentGateway.findAll", query = "SELECT p FROM PaymentGateway p"),
    @NamedQuery(name = "PaymentGateway.findById", query = "SELECT p FROM PaymentGateway p WHERE p.id = :id"),
    @NamedQuery(name = "PaymentGateway.findByName", query = "SELECT p FROM PaymentGateway p WHERE p.name = :name"),
    @NamedQuery(name = "PaymentGateway.findByDescription", query = "SELECT p FROM PaymentGateway p WHERE p.description = :description"),
    @NamedQuery(name = "PaymentGateway.findByRemoved", query = "SELECT p FROM PaymentGateway p WHERE p.removed = :removed")})
public class PaymentGateway implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "removed")
    private String removed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentId")
    private Collection<Invoice> invoiceCollection;

    public PaymentGateway() {
    }

    public PaymentGateway(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    @XmlTransient
    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
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
        if (!(object instanceof PaymentGateway)) {
            return false;
        }
        PaymentGateway other = (PaymentGateway) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PaymentGateway[ id=" + id + " ]";
    }
    
}
