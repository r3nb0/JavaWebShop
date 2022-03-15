package models;

import entities.Invoice;
import entities.InvoiceItem;
import java.math.BigDecimal;

public class OrderDetailModel {

    private String productName;
    private float subtotal;
    private float shipping;
    private float tax;
    private float total;

    public OrderDetailModel(String productName, String subtotal,
            String shipping, String tax, String total) {
        this.productName = productName;
        this.subtotal = Float.parseFloat(subtotal);
        this.shipping = Float.parseFloat(shipping);
        this.tax = Float.parseFloat(tax);
        this.total = Float.parseFloat(total);
    }

    public OrderDetailModel(Invoice invoice) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        this.productName = "";
        Double currentTotal;
        for (InvoiceItem item : invoice.getInvoiceItemCollection()) {
            this.productName = this.productName + "(" + item.getAmount().toString() + "x)" + item.getProductId().getName() + ", ";
            BigDecimal itemsCost = calculateCost(item.getAmount(), item.getPrice());
            currentTotal = Double.parseDouble(totalPrice.toString());
            totalPrice = BigDecimal.valueOf(Double.sum(itemsCost.doubleValue(), currentTotal));
        }
        this.productName = this.productName + " + (1x) Priority Shipping (10€)";
        this.subtotal = totalPrice.floatValue();
        this.tax = subtotal * (float) 0.25;
        this.subtotal -= tax;
        this.shipping = 10;
        this.total = subtotal + shipping + tax;
    }

    public OrderDetailModel() {
    }

    private BigDecimal calculateCost(int itemQuantity, BigDecimal itemPrice) {
        BigDecimal itemCost = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        itemCost = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));
        totalCost = totalCost.add(itemCost);
        return totalCost;
    }
    
    public String getProductName() {
        return productName;
    }

    public String getSubtotal() {
        return String.format("%.2f", subtotal);
    }

    public String getShipping() {
        return String.format("%.2f", shipping);
    }

    public String getTax() {
        return String.format("%.2f", tax);
    }

    public String getTotal() {
        return String.format("%.2f", total);
    }

}
