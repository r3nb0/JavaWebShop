/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
    @NamedQuery(name = "Invoice.findByMessage", query = "SELECT i FROM Invoice i WHERE i.message = :message"),
    @NamedQuery(name = "Invoice.findByTotalPrice", query = "SELECT i FROM Invoice i WHERE i.totalPrice = :totalPrice"),
    @NamedQuery(name = "Invoice.findByTimeOfOrder", query = "SELECT i FROM Invoice i WHERE i.timeOfOrder = :timeOfOrder"),
    @NamedQuery(name = "Invoice.findByRemoved", query = "SELECT i FROM Invoice i WHERE i.removed = :removed"),
    @NamedQuery(name = "Invoice.findByTransactionId", query = "SELECT i FROM Invoice i WHERE i.transactionId = :transactionId"),
    @NamedQuery(name = "Invoice.findByCompleted", query = "SELECT i FROM Invoice i WHERE i.completed = :completed")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "message")
    private String message;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalPrice")
    private BigDecimal totalPrice;
    @Column(name = "timeOfOrder")
    private String timeOfOrder;
    @Column(name = "removed")
    private String removed;
    @Column(name = "transactionId")
    private String transactionId;
    @Basic(optional = false)
    @Column(name = "completed")
    private String completed;
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account accountId;
    @JoinColumn(name = "paymentId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaymentGateway paymentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private Collection<InvoiceItem> invoiceItemCollection;

    public Invoice() {
    }

    public Invoice(Integer id) {
        this.id = id;
    }

    public Invoice(Integer id, String completed) {
        this.id = id;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(String timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public PaymentGateway getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(PaymentGateway paymentId) {
        this.paymentId = paymentId;
    }

    @XmlTransient
    public Collection<InvoiceItem> getInvoiceItemCollection() {
        return invoiceItemCollection;
    }

    public void setInvoiceItemCollection(Collection<InvoiceItem> invoiceItemCollection) {
        this.invoiceItemCollection = invoiceItemCollection;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Invoice[ id=" + id + " ]";
    }
    
}
