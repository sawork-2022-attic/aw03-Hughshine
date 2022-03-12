package com.example.webpos.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item _item = items.get(i);
            if (_item.getProduct().getId() == item.getProduct().getId()) {
                _item.setQuantity(_item.getQuantity() + item.getQuantity());
                return true;
            }
        }
        return items.add(item);
    }

    public boolean deleteOneProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            Item _item = items.get(i);
            if (_item.getProduct().getId() == product.getId()) {
                if (_item.getQuantity() == 1) {
                    items.remove(i);
                } else {
                    _item.setQuantity(_item.getQuantity()-1);
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteAllProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            Item _item = items.get(i);
            if (_item.getProduct().getId() == product.getId()) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total );

        return stringBuilder.toString();
    }

    public double total() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }
}
