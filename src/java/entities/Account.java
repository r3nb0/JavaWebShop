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

@Entity
@Table(name = "Account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByRole", query = "SELECT a FROM Account a WHERE a.role = :role"),
    @NamedQuery(name = "Account.findByRegisterIp", query = "SELECT a FROM Account a WHERE a.registerIp = :registerIp"),
    @NamedQuery(name = "Account.findByRemoved", query = "SELECT a FROM Account a WHERE a.removed = :removed"),
    @NamedQuery(name = "Account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;
    @Basic(optional = false)
    @Column(name = "registerIp")
    private String registerIp;
    @Basic(optional = false)
    @Column(name = "removed")
    private String removed;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<Invoice> invoiceCollection;

    public Account() {
    }

    public Account(Integer id) {
        this.id = id;
    }

    public Account(Integer id, String email, String password, String role, String registerIp, String removed, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registerIp = registerIp;
        this.removed = removed;
        this.username = username;
    }

    public Account(String email, String password, String role, String registerIp, String removed, String username) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registerIp = registerIp;
        this.removed = removed;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registerIp = registerIp;
        this.removed = removed;
        return "id=" + id + ";username=" + username + ";email=" + email + 
                ";password=" + password + ";role=" + role + ";ip=" + registerIp + "; removed=" + removed + ";";
    }
    
}
