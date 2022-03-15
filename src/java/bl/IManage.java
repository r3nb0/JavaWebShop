package bl;

import entities.Account;
import entities.AppLog;
import entities.Invoice;
import entities.PaymentGateway;
import entities.Product;
import entities.ProductCategory;
import java.util.Collection;
import models.CartModel;
import models.OrderDetailModel;
import models.UserModel;

public interface IManage {
    String getIp();
    //categories
    Collection<ProductCategory> getCategories();
    Collection<ProductCategory> getCategoriesForAdmin();
    ProductCategory getCategory(int categoryId);
    int insertCategory(ProductCategory category);
    ProductCategory editCategory(ProductCategory category);
    boolean removeCategory(int id);
    
    //products
    Collection<Product> getProductsWithinCategory(int categoryId);
    Collection<Product> getProducts();
    Collection<Product> getProductsForAdmin();
    Product getProduct(int productId);
    int insertProduct(Product product);
    Product editProduct(Product product);
    boolean removeProduct(int id);
    
    //invoices
    Collection<Invoice> getInvoices(boolean showRemoved);
    Collection<Invoice> getInvoicesForUser(int userId);
    Invoice getInvoice(int invoiceId);
    int insertInvoice(Invoice invoice);
    Invoice createInvoice(CartModel cart, UserModel user, String orderMessage, String paymentGatewayOption);
    Invoice editInvoice(Invoice invoice);
    boolean removeInvoice(int id);
    
    Invoice completePaymentForInvoice(String invoiceId, String transactionId);
    OrderDetailModel createOrderModel(CartModel cart);
    
    //payments
    Collection<PaymentGateway> getPaymentGateways(boolean showRemoved);
    PaymentGateway getPaymentGateway(int id);
    int insertPaymentGateway(PaymentGateway paymentGateway);
    PaymentGateway editPaymentGateway(PaymentGateway paymentGateway);
    boolean removePaymentGateway(int id);

    //accounts
    Account login(String email, String password);
    Account register(String email, String password);
    Collection<Account> getAccounts();

    Collection<AppLog> getLogs();
    void log(String name);
}
