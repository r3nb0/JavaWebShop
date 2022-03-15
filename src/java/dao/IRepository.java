package dao;

import entities.*;
import java.time.LocalDateTime;
import java.util.Collection;

public interface IRepository {

    //accounts login, register & crud
    Account register(String email, String password, String username, String ip);

    Account login(String username, String password, String ip);

    void forgotPassword(String email);

    Collection<Account> getAllAccounts();

    Account getAccount(int id);

    Account getAccount(String email);

    //application logs
    void addAppLog(String description, String location);

    Collection<AppLog> getAllAppLogs();

    //products crud
    Product getProduct(int id);

    int addProduct(Product product);

    Product editProduct(int id, Product product);

    boolean deleteProduct(int id);

    //prod - filter
    Collection<Product> searchProduct(String search);

    Collection<Product> getAllProducts();

    //product categories crud
    ProductCategory getProductCategory(int id);

    int addProductCategory(ProductCategory category);

    ProductCategory editProductCategory(int id, ProductCategory category);

    boolean deleteProductCategory(int id);

    Collection<ProductCategory> getAllProductCategories();

    Collection<ProductCategory> getAllProductCategoriesWithProducts();

    //invoice crud
    Invoice getInvoice(int id);

    int addInvoice(Invoice invoice);

    Invoice editInvoice(int id, Invoice invoice);

    boolean deleteInvoice(int id);

    //invoice - filters
    Collection<Invoice> getInvoicesForAccount(int userId);

    Collection<Invoice> getInvoicesFromToDate(LocalDateTime from, LocalDateTime to);

    Collection<Invoice> getAllInvoices();

    //payment gateways
    PaymentGateway getPaymentGateway(int id);

    int addPaymentGateway(PaymentGateway paymentGateway);

    PaymentGateway editPaymentGateway(int id, PaymentGateway paymentGateway);

    boolean deletePaymentGateway(int id);

    Collection<PaymentGateway> getAllPaymentGateways();
}
