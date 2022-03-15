package bl;

import dao.IRepository;
import dao.RepoFactory;
import entities.Account;
import entities.AppLog;
import entities.Invoice;
import entities.InvoiceItem;
import entities.PaymentGateway;
import entities.Product;
import entities.ProductCategory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import models.CartItem;
import models.CartModel;
import models.OrderDetailModel;
import models.UserModel;

public class Manager implements IManage {

    private IRepository repo;
    private String ip = "NO-IP";

    public Manager(String ip) {
        this.repo = RepoFactory.getInstance();
        this.ip = ip;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public Collection<ProductCategory> getCategories() {
        repo.addAppLog("Getting all categories", ip);
        List<ProductCategory> list = new ArrayList<>();
        for (ProductCategory pc : repo.getAllProductCategoriesWithProducts()) {
            if (pc.getRemoved().equalsIgnoreCase("NO") && pc.getProductCollection().size() > 0) {
                list.add(pc);
            }
        }
        return list;
    }
    public Collection<ProductCategory> getCategoriesForAdmin() {
        repo.addAppLog("Getting all categories for admin", ip);
        return repo.getAllProductCategories();
    }

    @Override
    public int insertCategory(ProductCategory category) {
        repo.addAppLog("Adding new category", ip);
        return repo.addProductCategory(category);
    }

    @Override
    public ProductCategory editCategory(ProductCategory category) {
        repo.addAppLog("Editing category", ip);
        return repo.editProductCategory(category.getId(), category);
    }

    @Override
    public boolean removeCategory(int id) {
        repo.addAppLog("Removing category", ip);
        return repo.deleteProductCategory(id);
    }

    @Override
    public Collection<Product> getProducts() {
        repo.addAppLog("Getting all products", ip);
        List<Product> list = new ArrayList<>();
        for (Product prod : repo.getAllProducts()) {
            if (prod.getRemoved().equalsIgnoreCase("NO")) {
                list.add(prod);
            }
        }
        return list;
    }
    public Collection<Product> getProductsForAdmin() {
        repo.addAppLog("Getting all products for admin!", ip);
        return repo.getAllProducts();
    }

    @Override
    public int insertProduct(Product product) {
        repo.addAppLog("Adding product", ip);
        return repo.addProduct(product);
    }

    @Override
    public Product editProduct(Product product) {
        repo.addAppLog("Editing product", ip);
        return repo.editProduct(product.getId(), product);
    }

    @Override
    public boolean removeProduct(int id) {
        repo.addAppLog("Removing product", ip);
        return repo.deleteProduct(id);
    }

    @Override
    public Collection<Invoice> getInvoices(boolean showRemoved) {
        repo.addAppLog("Getting all invoices", ip);
        
        Collection<Invoice> invoices = repo.getAllInvoices();
        List<Invoice> list = new ArrayList();
        for (Invoice inv : invoices) {
            if (inv.getRemoved().equalsIgnoreCase("NO")) {
                list.add(inv);
            }
        }
        return list;
    }

    @Override
    public Collection<Invoice> getInvoicesForUser(int userId) {
        repo.addAppLog("Getting invoices for user", ip);
        Collection<Invoice> invoices = repo.getInvoicesForAccount(userId);
        List<Invoice> list = new ArrayList();
        for (Invoice inv : invoices) {
            if (inv.getRemoved().equalsIgnoreCase("NO")) {
                list.add(inv);
            }
        }
        return list;
    }

    @Override
    public int insertInvoice(Invoice invoice) {
        repo.addAppLog("added new invoice", ip);
        return repo.addInvoice(invoice);
    }

    @Override
    public Invoice editInvoice(Invoice invoice) {
        repo.addAppLog("Editing invoice #" + invoice.getId(), ip);
        invoice.setTimeOfOrder(invoice.getTimeOfOrder() + "( updated@" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + ")");
        return repo.editInvoice(invoice.getId(), invoice);
    }

    @Override
    public boolean removeInvoice(int id) {
        repo.addAppLog("Removing invoice", ip);
        return repo.deleteInvoice(id);
    }

    @Override
    public Collection<PaymentGateway> getPaymentGateways(boolean showRemoved) {
        repo.addAppLog("Getting all payment gateways", ip);
        Collection<PaymentGateway> pgs = repo.getAllPaymentGateways();
        List<PaymentGateway> list = new ArrayList();
        for (PaymentGateway pg : pgs) {
            if (pg.getRemoved().equalsIgnoreCase("NO")) {
                list.add(pg);
            }
        }
        return list;
    }

    @Override
    public int insertPaymentGateway(PaymentGateway paymentGateway) {
        repo.addAppLog("Adding new payment gateway", ip);
        return repo.addPaymentGateway(paymentGateway);
    }

    @Override
    public PaymentGateway editPaymentGateway(PaymentGateway paymentGateway) {
        repo.addAppLog("editing payment gateway", ip);
        return repo.editPaymentGateway(paymentGateway.getId(), paymentGateway);
    }

    @Override
    public boolean removePaymentGateway(int id) {
        repo.addAppLog("removing payment gateway", ip);
        return repo.deletePaymentGateway(id);
    }

    @Override
    public Collection<Product> getProductsWithinCategory(int categoryId) {
        Collection<Product> list = new ArrayList<>();
        for (Product p : repo.getAllProducts()) {
            if (p.getRemoved().equalsIgnoreCase("NO") && 
                    p.getProductCategoryId().getId() == categoryId) {
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public ProductCategory getCategory(int i) {
        return repo.getProductCategory(i);
    }

    @Override
    public Product getProduct(int i) {
        return repo.getProduct(i);
    }

    @Override
    public Invoice getInvoice(int i) {
        return repo.getInvoice(i);
    }

    @Override
    public PaymentGateway getPaymentGateway(int i) {
        return repo.getPaymentGateway(i);
    }

    @Override
    public Account login(String username, String password) {
        log("Logged in successfully! " + username);
        return repo.login(username, password, getIp());
    }

    @Override
    public Account register(String email, String password) {
        String username = email.substring(0, email.indexOf('@'));
        Account acc = repo.register(email, password, username, getIp());
        if (acc != null) {
            log("Account successfully registered! " + email);
            return acc;
        } else {
            return null;
        }
    }

    @Override
    public Collection<Account> getAccounts() {
        return repo.getAllAccounts();
    }

    @Override
    public void log(String description) {
        repo.addAppLog(description, ip);
    }

    @Override
    public OrderDetailModel createOrderModel(CartModel cart) {
        String products = "";
        String subtotal = "";
        String total = "";
        String shipping = "10";
        String tax = "";

        BigDecimal itemsTotal = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            products.concat(item.getItemName() + " x" + item.getAmount() + ", ");
            itemsTotal.add(item.getPrice());
        }
        products.concat(", shipping (GLS) 10kn");
        itemsTotal.add(new BigDecimal("10"));

        total = itemsTotal.toString();
        BigDecimal[] taxAndTotalResults = itemsTotal.divideAndRemainder(new BigDecimal("25"));
        subtotal = taxAndTotalResults[0].toString();
        tax = taxAndTotalResults[1].toString();

        return new OrderDetailModel(products, subtotal, shipping, tax, total);

    }

    @Override
    public Invoice createInvoice(CartModel cart, UserModel user, String orderMessage, 
            String paymentOption) {
        Invoice invoice = new Invoice();
        invoice.setId(0);
        invoice.setAccountId(repo.getAccount(user.getId()));
        invoice.setMessage(orderMessage);
        invoice.setTotalPrice(BigDecimal.ZERO);
        Collection<InvoiceItem> invoiceItemCollection = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            InvoiceItem i = new InvoiceItem();
            i.setProductId(getProduct(item.getId()));
            i.setAmount(item.getAmount());
            i.setPrice(item.getPrice());
            i.setRemoved("NO");
            invoiceItemCollection.add(i);
            invoice.setTotalPrice(BigDecimal.valueOf(Double.sum(invoice.getTotalPrice().doubleValue(), i.getPrice().doubleValue() * i.getAmount().doubleValue())));
        }
        BigDecimal shippingCost = new BigDecimal("10");
        invoice.setTotalPrice(BigDecimal.valueOf(Double.sum(invoice.getTotalPrice().doubleValue(), shippingCost.doubleValue())));
        invoice.setInvoiceItemCollection(invoiceItemCollection);
        if (paymentOption.equalsIgnoreCase("paypal")) {
            invoice.setPaymentId(findPaymentByName(paymentOption));        
            invoice.setCompleted("NO");

        } else if (paymentOption.equalsIgnoreCase("pay on delivery")) {
            invoice.setPaymentId(findPaymentByName(paymentOption));
            invoice.setCompleted("COMPLETED");
        } else {
            return null;
        }
        for (InvoiceItem item : invoiceItemCollection) {
            item.setInvoiceId(invoice);
        }
        return invoice;
    }

    private PaymentGateway findPaymentByName(String paymentOption) {
        for (PaymentGateway pg : repo.getAllPaymentGateways()) {
            if (pg.getName().equalsIgnoreCase(paymentOption)) {
                return pg;
            }
        }
        return null;
    }

    @Override
    public Invoice completePaymentForInvoice(String invoiceId, String transactionId) {
        Invoice invoice = getInvoice(Integer.valueOf(invoiceId));
        if (invoice != null) {
            invoice.setCompleted("COMPLETED");
            invoice.setTransactionId(transactionId);
            return editInvoice(invoice);
        }
        return null;
    }

    @Override
    public Collection<AppLog> getLogs() {
        List<AppLog> list = new ArrayList<>();
        for (AppLog log : repo.getAllAppLogs()) {
//            if (log.getDescription().toLowerCase().contains("regist")
//                    || log.getDescription().toLowerCase().contains("log")) {
//                list.add(log);
//            }
                list.add(log);
        }
        return list;
    }

}
