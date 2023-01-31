/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Cart {

    private List<ProductInCart> items;
    
    public Cart() {
        items = new ArrayList<>();
    }

    public List<ProductInCart> getItems() {
        return items;
    }

    public int getQuantityById(String productId) {
        return getProductInCartById(productId).getQuantity();
    }

    private ProductInCart getProductInCartById(String productId) {
        for (ProductInCart prd : items) {
            if (prd.getProduct().getProductId().equals(productId)) {
                return prd;
            }
        }
        return null;
    }

    public void addProduct(ProductInCart prd) {
        if (getProductInCartById(prd.getProduct().getProductId()) != null) {
            ProductInCart p = getProductInCartById(prd.getProduct().getProductId());
            p.setQuantity(p.getQuantity() + prd.getQuantity());
        } else {
            items.add(prd);
        }
    }

    public void removeProduct(String productId) {
        if (getProductInCartById(productId) != null) {
            items.remove(getProductInCartById(productId));
        }
    }

    public double getTotalMoney() {
        double money = 0;
        for (ProductInCart prd : items) {
            money += (prd.getQuantity() * prd.getPrice());
        }
        return money;
    }

    private Product getProductById(String productId, List<Product> list) {
        for (Product p : list) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    public Cart(String txt, List<Product> list) {
        items = new ArrayList<>();
        try {
            if (txt != null && txt.length() != 0) {
                String[] s = txt.split("-");
                for (String i : s) {
                    String[] n = i.split(":");
                    String productId = n[0];
                    int quantity = Integer.parseInt(n[1]);
                    Product p = getProductById(productId, list);
                    ProductInCart t = new ProductInCart(p, quantity,p.getPrice()-p.getDiscount());
                    addProduct(t);
                }
            }
        } catch (NumberFormatException e) {
        }

    }
}
