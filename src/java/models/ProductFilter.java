/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author r3nb0
 */
public class ProductFilter {

    private String categories;
    private String sort;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String search;

    public ProductFilter() {
        this.categories = "";
    }

    public ProductFilter(String categories, String sort, BigDecimal minPrice, BigDecimal maxPrice, String search) {
        this.categories = categories;
        this.sort = sort;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.search = search;
    }

    public String getCategories() {
        return categories;
    }

    public ArrayList<String> getCategoriesArray() {
        return new ArrayList<String>(Arrays.asList(this.categories.split("#")));
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSort() {
        return sort.toLowerCase() != new String() ? sort.toLowerCase() : "";
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String value) {
        if (isNumeric(value)) {
            try {
                int a = Integer.valueOf(value);
                this.minPrice = new BigDecimal(Double.valueOf(a));
            } catch (Exception e) {
                int a = Integer.parseInt(value);
                float b = a;
                this.minPrice = new BigDecimal(Double.valueOf(b));
            }
        } else {
            this.minPrice = new BigDecimal("1");
        }
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

    public void setMaxPrice(String value) {
        if (isNumeric(value)) {
            try {
                int a = Integer.valueOf(value);
                this.maxPrice = new BigDecimal(Double.valueOf(a));
            } catch (Exception e) {
                int a = Integer.parseInt(value);
                float b = a;
                this.maxPrice = new BigDecimal(Double.valueOf(b));
            }
        } else {
            this.maxPrice = new BigDecimal("100000");
        }
    }

    public String getSearch() {
        return search.toLowerCase();
    }

    public void setSearch(String search) {
        this.search = search.toLowerCase();
    }

    @Override
    public String toString() {
        return "ProductFilter{"
                + "categories={" + categories.toString()
                + "}, sort={" + sort.toString()
                + "}, minPrice={" + minPrice.toString()
                + "}, maxPrice={" + maxPrice.toString()
                + "}, search={" + search.toString() + "}}";
    }

    public void addCategory(String[] val) {
        for (String s : val) {
            this.categories = categories + s + "#";
        }
    }
}
