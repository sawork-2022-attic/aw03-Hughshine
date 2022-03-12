package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PosController {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    private void basicRender(Model model) {
        model.addAttribute("products", posService.products());
        Cart cart = posService.getCart();
        double discount = posService.discount();
        double tax = posService.tax();
        double total = posService.total(cart);
        model.addAttribute("cart", cart); 
        model.addAttribute("tax", "" + tax*100 + "%"); // TODO        
        model.addAttribute("discount", "" + discount*100 + "%"); // TODO        
        model.addAttribute("subtotal", total);        
        model.addAttribute("total", total * (1 - discount) * (1 + tax));   
    }

    @GetMapping("/")
    public String pos(Model model) {
        basicRender(model);
        return "index";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, Model model) {
        posService.add(id, 1);
        basicRender(model);
        return "redirect:/";
    }

    @GetMapping("/sub/{id}")
    public String sub(@PathVariable("id") String id, Model model) {
        posService.deleteOne(id);
        basicRender(model);
        return "redirect:/";
    }

    @GetMapping("/removeAll/{id}")
    public String removeAll(@PathVariable("id") String id, Model model) {
        posService.deleteAll(id);
        basicRender(model);
        return "redirect:/";
    }
    
    @GetMapping("/charge/")
    public String charge(Model model) {
        posService.checkout(posService.getCart());
        basicRender(model);
        return "redirect:/";
    }

    @GetMapping("/clearAll/")
    public String clearAll(Model model) {
        posService.clearCart(posService.getCart());
        basicRender(model);
        return "redirect:/";
    }
}
