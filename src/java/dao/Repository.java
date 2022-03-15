package dao;

import entities.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import services.MailingService;

public class Repository implements IRepository {

    @PersistenceUnit
    private final EntityManagerFactory emf;

    public enum Roles {
        User, Admin
    }

    public Repository() {
        emf = Persistence.createEntityManagerFactory("javawebPU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Account register(String email, String password, String username, String ip) {

        if (getAccount(email) != null) {
            return null;
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Account account = new Account(email, password, Roles.User.toString(), ip, "NO", username);
            em.persist(account);
            em.getTransaction().commit();
            addAppLog("REGISTER: " + account.getEmail() + "]", ip);
            return account;
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "repo - register - " + ip);
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public Account login(String email, String password, String ip) {
        EntityManager em = getEntityManager();
        Account acc = null;
        List<Account> list = new ArrayList<>();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT acc FROM Account acc WHERE acc.email='" + email + "' AND acc.password='" + password + "'");
            list = query.getResultList();
            if (list.size() == 0) {
                addAppLog("LOGIN FAILED!" + email + "]", ip);
                throw new EntityNotFoundException("Can't find account with specified email. Email: " + email + ".");
            } else {
                acc = list.get(0);
                //addAppLog("LOGIN: " + acc.getEmail() + " w/ role:" + acc.getRole(), ip);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            //addAppLog("list size=[" + list.size() + "] " + e.getLocalizedMessage() + e.getStackTrace() + e.getSuppressed() + e.getCause() + e.getMessage(), ip);
        } finally {
            em.close();
        }
        return acc;
    }

    @Override
    public void forgotPassword(String email) {
        Account acc = null;
        acc = getAccount(email);
        try {
            if (acc != null) {
                new MailingService().sendPasswordOnEmail(acc.getPassword(), acc.getEmail());
                //addAppLog("Forgot password request! Password sent on owners email! Email: " + email + ". ", "NO-IP");
            } else {
                throw new EntityNotFoundException("Account with that email was not found! Please spellcheck email address.");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
    }

    @Override
    public Collection<Account> getAllAccounts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> rootEntry = cq.from(Account.class);
            CriteriaQuery<Account> all = cq.select(rootEntry);
            TypedQuery<Account> allQuery = em.createQuery(all);
            Collection<Account> list = allQuery.getResultList();
            return list;
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public void addAppLog(String description, String location) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            AppLog log = new AppLog();
            log.setDescription(description);
            log.setLocation(location);
            log.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + " " + LocalDateTime.now().toLocalTime());
            log.setRemoved("NO");
            em.persist(log);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<AppLog> getAllAppLogs() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<AppLog> cq = cb.createQuery(AppLog.class);
            Root<AppLog> rootEntry = cq.from(AppLog.class);
            CriteriaQuery<AppLog> all = cq.select(rootEntry);
            TypedQuery<AppLog> allQuery = em.createQuery(all);
            //addAppLog("Get all app logs", "NO-IP");
            return allQuery.getResultList();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public Product getProduct(int id) {
        EntityManager em = getEntityManager();
        try {
            Product p = em.find(Product.class, id);
            if (p == null) {
                throw new EntityNotFoundException("Can't find product with specified id. Id: " + id + ".");
            } else {
                return p;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public int addProduct(Product product) {
        product.setRemoved("NO");
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            return product.getId();
        } catch (Exception e) {
            //addAppLog("product added [name=" + product.getName() + "]", "NO-IP");
        } finally {
            em.close();
        }
        return 0;
    }

    @Override
    public Product editProduct(int id, Product newProduct) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Product dbProd = em.find(Product.class, id);
            if (dbProd != null) {
                dbProd.setDescription(newProduct.getDescription());
                dbProd.setImage(newProduct.getImage());
                dbProd.setInvoiceItemCollection(newProduct.getInvoiceItemCollection());
                dbProd.setName(newProduct.getName());
                dbProd.setProductCategoryId(newProduct.getProductCategoryId());
                dbProd.setPrice(newProduct.getPrice());
                em.getTransaction().commit();
                return dbProd;
            } else {
                throw new EntityNotFoundException("Product with id" + id + " cannot be found! No changes were made.");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        EntityManager em = getEntityManager();
        boolean removed = false;
        try {
            em.getTransaction().begin();
            Product prod = em.find(Product.class, id);
            if (prod != null) {
                prod.setRemoved("YES");
                em.getTransaction().commit();
                removed = true;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return removed;
    }

    @Override
    public Collection<Product> searchProduct(String search) {
        Collection<Product> list = new ArrayList<Product>();
        Collection<Product> products = getAllProducts();
        for (Product prod : products) {
            if (prod.getName().toLowerCase().contains(search)
                    || prod.getDescription().toLowerCase().contains(search)
                    || prod.getProductCategoryId().getName().contains(search)) {
                list.add(prod);
            }
        }
        return list;
    }

    @Override
    public Collection<Product> getAllProducts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> rootEntry = cq.from(Product.class);
            CriteriaQuery<Product> all = cq.select(rootEntry);
            TypedQuery<Product> allQuery = em.createQuery(all);
            //addAppLog("Get all products", "NO-IP");
            return allQuery.getResultList();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public ProductCategory getProductCategory(int id) {
        EntityManager em = getEntityManager();
        try {
            ProductCategory pc = em.find(ProductCategory.class, id);
            if (pc == null) {
                throw new EntityNotFoundException("Can't find product with specified id. Id: " + id + ".");
            } else {
                //addAppLog("Product category find by id. [id=" + pc.getId() + "]", "NO-IP");
                return pc;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public int addProductCategory(ProductCategory category) {
        category.setRemoved("NO");
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
            //addAppLog("product category added [name=" + category.getName() + "]", "NO-IP");
            return category.getId();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return 0;
    }

    @Override
    public ProductCategory editProductCategory(int id, ProductCategory newCategory) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ProductCategory dbPC = em.find(ProductCategory.class, id);
            if (dbPC != null) {
                dbPC.setDescription(newCategory.getDescription());
                dbPC.setName(newCategory.getName());
                dbPC.setRemoved(newCategory.getRemoved());
                em.getTransaction().commit();
                return dbPC;
            } else {
                throw new EntityNotFoundException("ProductCategory with id" + id + " cannot be found! No changes were made.");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean deleteProductCategory(int id) {
        EntityManager em = getEntityManager();
        boolean removed = false;
        try {
            em.getTransaction().begin();
            ProductCategory cat = em.find(ProductCategory.class, id);
            if (cat != null) {
                cat.setRemoved("YES");
                em.getTransaction().commit();
                removed = true;
            } else {
                throw new EntityNotFoundException("Payment gateway with id=" + id + "cannon be found. Thus is not removed!");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return removed;
    }

    @Override
    public Collection<ProductCategory> getAllProductCategories() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductCategory> cq = cb.createQuery(ProductCategory.class);
            Root<ProductCategory> rootEntry = cq.from(ProductCategory.class);
            CriteriaQuery<ProductCategory> all = cq.select(rootEntry);
            TypedQuery<ProductCategory> allQuery = em.createQuery(all);
            //addAppLog("Get all categories", "NO-IP");
            return allQuery.getResultList();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
        return null;
    }

    @Override
    public Collection<ProductCategory> getAllProductCategoriesWithProducts() {
        Collection<ProductCategory> list = new ArrayList<>();
        for (ProductCategory pc : getAllProductCategories()) {
            if (pc.getProductCollection().size() != 0) {
                list.add(pc);
            }
        }
        return list;
    }

    @Override
    public Invoice getInvoice(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Invoice i = em.find(Invoice.class, id);
            if (i == null) {
                throw new EntityNotFoundException("Can't find invoice with specified id. Id: " + id + ".");
            } else {
                return i;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
        return null;
    }

    @Override
    public int addInvoice(Invoice invoice) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            invoice.setTimeOfOrder(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            invoice.setRemoved("NO");
            em.persist(invoice);
            em.getTransaction().commit();
            return invoice.getId();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return 0;
    }

    @Override
    public Invoice editInvoice(int id, Invoice newInvoice) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Invoice dbInvoice = em.find(Invoice.class, id);
            if (dbInvoice != null) {
                dbInvoice.setInvoiceItemCollection(newInvoice.getInvoiceItemCollection());
                dbInvoice.setCompleted(newInvoice.getCompleted());
                dbInvoice.setTransactionId(newInvoice.getTransactionId());
                dbInvoice.setRemoved(newInvoice.getRemoved());
                em.getTransaction().commit();
                return dbInvoice;
            } else {
                throw new EntityNotFoundException("Invoice with id" + id + " cannot be found! No changes were made.");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean deleteInvoice(int id) {
        EntityManager em = getEntityManager();
        boolean removed = false;
        try {
            em.getTransaction().begin();
            Invoice invoice = em.find(Invoice.class, id);
            if (invoice != null) {
                invoice.setRemoved("YES");
                em.getTransaction().commit();
                removed = true;
            } else {
                throw new EntityNotFoundException("Invoice with id=" + id + "cannon be found. Thus is not removed!");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return removed;
    }

    @Override
    public Collection<Invoice> getInvoicesForAccount(int userId) {
        EntityManager em = getEntityManager();
        TypedQuery<Invoice> query = em.createQuery("SELECT i FROM Invoice i WHERE i.accountId = :id0", Invoice.class);
        Account acc = new Account();
        acc.setId(userId);
        Collection<Invoice> invoices = query.setParameter("id0", acc).getResultList();
        List<Invoice> list = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (invoice.getCompleted().equalsIgnoreCase("COMPLETED")) {
                list.add(invoice);
            }
        }
        return list;
    }

    @Override
    public Collection<Invoice> getInvoicesFromToDate(LocalDateTime from, LocalDateTime to) {
        EntityManager em = getEntityManager();
        return em.createQuery("select i from invoice as i where timeOfOrder between " + from.format(DateTimeFormatter.ISO_DATE) + " and " + to.format(DateTimeFormatter.ISO_DATE))
                .getResultList();
    }

    @Override
    public Collection<Invoice> getAllInvoices() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
            Root<Invoice> rootEntry = cq.from(Invoice.class);
            CriteriaQuery<Invoice> all = cq.select(rootEntry);
            TypedQuery<Invoice> allQuery = em.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
        return null;
    }

    @Override
    public PaymentGateway getPaymentGateway(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            PaymentGateway gateway = em.find(PaymentGateway.class, id);
            if (gateway == null) {
                throw new EntityNotFoundException("Can't find payment gateway with specified id. Id: " + gateway.getId() + ".");
            } else {
                //addAppLog("PaymentGateway find by id. Id: " + gateway.getId() + ". ", "NO-IP");
                return gateway;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
        return null;
    }

    @Override
    public int addPaymentGateway(PaymentGateway paymentGateway) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(paymentGateway);
            em.getTransaction().commit();
            return paymentGateway.getId();
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return 0;
    }

    @Override
    public PaymentGateway editPaymentGateway(int id, PaymentGateway newPaymentGateway) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            PaymentGateway dbPG = em.find(PaymentGateway.class, id);
            if (dbPG != null) {
                newPaymentGateway.setId(id);
                dbPG = newPaymentGateway;
                em.getTransaction().commit();
                return dbPG;
            } else {
                throw new EntityNotFoundException("Payment gateway with id" + id + " cannot be found! No changes were made.");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean deletePaymentGateway(int id) {
        EntityManager em = getEntityManager();
        boolean removed = false;
        try {
            em.getTransaction().begin();
            PaymentGateway pg = em.find(PaymentGateway.class, id);
            if (pg != null) {
                pg.setRemoved("YES");
                em.getTransaction().commit();
                removed = true;
            } else {
                throw new EntityNotFoundException("Payment gateway with id=" + id + "cannon be found. Thus is not removed!");
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return removed;
    }

    @Override
    public Collection<PaymentGateway> getAllPaymentGateways() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PaymentGateway> cq = cb.createQuery(PaymentGateway.class);
            Root<PaymentGateway> rootEntry = cq.from(PaymentGateway.class);
            CriteriaQuery<PaymentGateway> all = cq.select(rootEntry);
            TypedQuery<PaymentGateway> allQuery = em.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            //addAppLog(e.getMessage(), "NO-IP");
        }
        return null;
    }

    @Override
    public Account getAccount(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Account a = em.find(Account.class, id);
            if (a == null) {
                throw new EntityNotFoundException("Can't find account with specified id. Id: " + id + ".");
            } else {
                //addAppLog("Invoice find by id. Id: " + id + ". ", "NO-IP");
                return a;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
        return null;
    }

    @Override
    public Account getAccount(String email) {
        EntityManager em = getEntityManager();
        try {
            Account a = (Account) em.createNamedQuery("Account.findByEmail").setParameter("email", email)
                    .getResultList().get(0);
            if (a == null) {
                throw new EntityNotFoundException("Can't find account with specified email. Email: " + email + ".");
            } else {
                //addAppLog("Account find by email. Email: " + email + ". ", "NO-IP");
                return a;
            }
        } catch (Exception e) {
            //addAppLog(e.getLocalizedMessage(), "NO-IP");
        }
        return null;
    }
}
