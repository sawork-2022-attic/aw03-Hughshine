package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {
        Cart cart = posDB.getCart();
        if (cart == null){
            cart = this.newCart();
        }
        return cart;
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public double discount() {
        return 0.05;
    }

    @Override
    public double tax() {
        return 0.05;
    }


    @Override
    public void clearCart(Cart cart) {
        if (cart != null) {
            cart.getItems().clear();
        }
    }

    @Override
    public boolean modifyCart(Cart cart, int itemId, int newAmount) {
        if (cart != null) {
            if (itemId > cart.getItems().size() || itemId <= 0) return false;
            Item item = cart.getItems().get(itemId-1);
            if (newAmount <= 0) {
                cart.getItems().remove(itemId-1);
            } else {
                item.setQuantity(newAmount);
            }
        }
        return true;
    }

    @Override
    public double checkout(Cart cart) {
        if (cart == null) {
            return 0;
        }
        double total = cart.total();
        clearCart(cart);
        return total;
    }

    @Override
    public double total(Cart cart) {
        if (cart == null) {
            return 0;
        }
        return cart.total();
    }

    @Override
    public boolean add(Product product, int amount) {
        return false;
    }

    @Override
    public boolean add(String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return false;

        return this.getCart().addItem(new Item(product, amount));
    }

    @Override
    public boolean deleteOne(String productId) {
        Product product = posDB.getProduct(productId);
        if (product == null) return false;
        return this.getCart().deleteOneProduct(product);
    }

    @Override
    public boolean deleteAll(String productId) {
        Product product = posDB.getProduct(productId);
        if (product == null) return false;
        return this.getCart().deleteAllProduct(product);
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }
}
