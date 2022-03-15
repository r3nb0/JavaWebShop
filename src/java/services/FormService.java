/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.math.BigDecimal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import models.ProductFilter;

/**
 *
 * @author r3nb0
 */
public class FormService {

    public static ProductFilter getProductFilterFromParamMap(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        ProductFilter filterSettings = new ProductFilter();

        if (!paramMap.entrySet().isEmpty()) {
            boolean categoryAdded = false;
            for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
                String key = param.getKey();
                String[] val = param.getValue();
                switch (key.toLowerCase()) {
                    case "categories":
                        filterSettings.addCategory(val);
                        categoryAdded = true;
                        break;
                    case "sort":
                        filterSettings.setSort(val[0]);
                        break;
                    case "search":
                        filterSettings.setSearch(val[0]);
                        break;
                    case "minprice":
                        filterSettings.setMinPrice(val[0]);
                        break;
                    case "maxprice":
                        filterSettings.setMaxPrice(val[0]);
                        break;
                    default:
                        break;
                }
            }
            if (!categoryAdded) {
                String[] st = {"-1"};
                filterSettings.addCategory(st);
            }
        } else {
            filterSettings.setCategories("-1");
            filterSettings.setMaxPrice("100000");
            filterSettings.setMinPrice("1");
            filterSettings.setSearch("");
            filterSettings.setSort("lh");
        }
        return filterSettings;
    }
}
