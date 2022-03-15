package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class CartModel implements Serializable {

    private Collection<CartItem> items;
    private BigDecimal total = BigDecimal.ZERO;

    public CartModel() {
        items = new ArrayList<CartItem>();
    }

    public CartModel(Collection<CartItem> items) {
        this.items = items;
        for (CartItem i : items) {
            total.add(BigDecimal.valueOf(0));
        }
    }

    public Collection<CartItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        updateTotalPrice();
        return this.total;
    }

    public void setItems(Collection<CartItem> items) {
        this.items = items;
    }

    public void add(CartItem item) {
        if (items != null && items.size() > 0) {
            boolean containsItem = false;
            for (CartItem i : items) {
                if (i.getId() == item.getId()) {
                    i.setAmount(i.getAmount() + item.getAmount());
                    containsItem = true;
                    break;
                }
            }
            if (!containsItem) {
                items.add(item);
            }
        } else {
            items = new ArrayList<CartItem>();
            items.add(item);
        }
        updateTotalPrice();
    }

    public void remove(CartItem item) {
        CartItem toRemove = null;
        if (items != null && items.size() > 0) {
            for (CartItem i : items) {
                if (i.getId() == item.getId()) {
                    toRemove = i;
                }
            }
            items.remove(toRemove);
            
        }
    }

    private void updateTotalPrice() {
        this.total = BigDecimal.ZERO;
        Double currentTotal;
        if (this.items != null) {
            for (CartItem item : items) {
                BigDecimal itemsCost = calculateCost(item.getAmount(), item.getPrice());
                currentTotal = total.doubleValue();
                this.total = BigDecimal.valueOf(Double.sum(itemsCost.doubleValue(), currentTotal));
            }
        }
    }

    public BigDecimal calculateCost(int itemQuantity, BigDecimal itemPrice) {
        BigDecimal itemCost = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        itemCost = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));
        totalCost = totalCost.add(itemCost);
        return totalCost;
    }

    public void changeItemAmount(CartItem item) {
        for (CartItem i : items) {
            if (i.getId() == item.getId()) {
                i.setAmount(item.getAmount());
                return;
            }
        }
        updateTotalPrice();
    }
}
