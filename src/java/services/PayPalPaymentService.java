package services;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import entities.Account;
import java.util.ArrayList;
import java.util.List;
import models.OrderDetailModel;

public class PayPalPaymentService {

    private static final String CLIENT_ID = "ARmWt3Pzdvk_YZk0c5j0P8bCcP845AawlB0jVclQFgegBe_gFO-WTudbMFo_Nj0CD60xPcQipK_MQOjj";
    private static final String CLIENT_SECRET = "ENCsaYmBCtbIfeQEV3ZKizNbbYJokowRB57KBB9hLgclCJHJlheWnPiye20BYDll5UbntCnpOb_7xZha";
    private static final String MODE = "sandbox";
    private String baseUrl = "";

    public PayPalPaymentService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String authorizePayment(OrderDetailModel orderDetail, Account account, int invoiceId) throws PayPalRESTException {
        Payer payer = getPayerInformation(account);
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail, String.valueOf(invoiceId));

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction)
                .setRedirectUrls(redirectUrls)
                .setPayer(payer)
                .setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
            }
        }
        return approvalLink;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls urls = new RedirectUrls();
        urls.setCancelUrl(baseUrl + "/order/cancel");
        urls.setReturnUrl(baseUrl + "/order/review");
        return urls;
    }

    private Payer getPayerInformation(Account account) {
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(account.getUsername());
        payerInfo.setLastName(account.getUsername());
        payerInfo.setEmail(account.getEmail());
        Payer payer = new Payer();
        payer.setPayerInfo(payerInfo);
        payer.setPaymentMethod("PayPal");
        return payer;
    }

    private List<Transaction> getTransactionInformation(OrderDetailModel orderDetail, String invoiceId) {
        Details details = new Details();
        details.setShipping(orderDetail.getShipping());
        details.setSubtotal(orderDetail.getSubtotal());
        details.setTax(orderDetail.getTax());

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());
        transaction.setItemList(getItemList(orderDetail));
        transaction.setInvoiceNumber(invoiceId);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution pe = new PaymentExecution();
        pe.setPayerId(payerId);
        Payment payment = new Payment().setId(paymentId);
        APIContext api = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(api, pe);
    }

    private ItemList getItemList(OrderDetailModel orderDetail) {
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setCurrency("EUR")
                .setName(orderDetail.getProductName())
                .setPrice(orderDetail.getSubtotal())
                .setTax(orderDetail.getTax())
                .setQuantity("1");
        items.add(item);
        itemList.setItems(items);
        return itemList;
    }
}
