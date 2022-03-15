package services;

import bl.Manager;
import entities.Product;
import entities.ProductCategory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.net.ssl.ManagerFactoryParameters;
import models.ProductFilter;

/**
 *
 * @author r3nb0
 */
public class FilterService {

    public static Collection<Product> filterProducts(Collection<Product> unfiltered, ProductFilter filter) {
        ArrayList<Product> filtered = new ArrayList<>();

        for (Product p : unfiltered) {
            boolean valid = true;

            //search name/desc. check - PASSED
            if (filter.getSearch().trim().isEmpty() || filter.getSearch().isEmpty()) {
                valid = true;
            } else {
                if (p.getName().toLowerCase().contains(filter.getSearch().toLowerCase())
                        || p.getDescription().toLowerCase().contains(filter.getSearch().toLowerCase())) {
                    valid = true;
                } else {
                    valid = false;
                }
            }

            //price check - PASSED partially
            //1 -> prvi je veci, 0 -> jednaki su, -1 -> drugi je veci
            if (valid && filter.getMinPrice().compareTo(p.getPrice()) <= 0
                    && filter.getMaxPrice().compareTo(p.getPrice()) >= 0) {
                valid = true;
            } else {
                valid = false;
            }

            //category check - PASSED
            if (valid && filter.getCategories().contains("-1")) {
                valid = true;
            } else {
                valid = isInCategory(p.getProductCategoryId().getId().toString(), filter.getCategoriesArray());
            }

            //if product is valid for this filter -> add to list -- PASSED
            if (valid) {
                filtered.add(p);
            }
        }

        if (!filtered.isEmpty()) {
            switch (filter.getSort()) {
                case "lh":
                    Collections.sort(filtered, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getPrice().compareTo(p2.getPrice());
                        }
                    });
                    break;
                case "hl":
                    Collections.sort(filtered, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getPrice().compareTo(p1.getPrice());
                        }
                    });
                    break;
                case "az":
                    Collections.sort(filtered, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getName().compareTo(p2.getName());
                        }
                    });
                    break;
                case "za":
                    Collections.sort(filtered, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getName().compareTo(p1.getName());
                        }
                    });
                    break;
            }
        } else {
            Product p = new Product();
            p.setId(0);
            p.setName("No objects in filtered product list!");
            p.setDescription("Size of FILTERED product list in FilterService.java is equal to 0.");
            p.setPrice(BigDecimal.ONE);
            p.setRemoved("FALSE");

            filtered.add(p);
        }
        return filtered;
    }

    //return true if it's contained, false if it's not
    private static boolean isInCategory(String catId, ArrayList<String> categoriesArray) {
        for (String string : categoriesArray) {
            if (catId.equals(string)) {
                return true;
            }
        }
        return false;
    }

}
