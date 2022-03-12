package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.List;

public interface PosService {
    public Cart getCart();

    public Cart newCart();

    public double tax();

    public double discount();

    public double checkout(Cart cart);

    public double total(Cart cart);

    public boolean add(Product product, int amount);

    public boolean add(String productId, int amount);

    public boolean deleteOne(String productId);

    public boolean deleteAll(String productId);


    public void clearCart(Cart cart);

    public boolean modifyCart(Cart cart, int itemId, int newAmount);

    public List<Product> products();
}
