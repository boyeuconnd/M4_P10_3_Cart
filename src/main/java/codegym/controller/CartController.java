package codegym.controller;


import codegym.model.Cart;
import codegym.model.Product;
import codegym.repository.ProductReposioty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;

@Controller
@SessionAttributes("cart")
public class CartController {

    @Autowired
    private ProductReposioty productReposioty;


    @ModelAttribute("cart")
    public HashMap<Long,Cart> myCart(){
        return new HashMap<>();
    }


    @GetMapping("/addcart/{id}")
    public String addCart(@ModelAttribute("cart")HashMap<Long,Cart> cart, @PathVariable Long id){

        if(cart==null){
            cart= new HashMap<>();
        }
        Product pickProduct = productReposioty.findOne(id);
        if(pickProduct!=null){
            if(cart.containsKey(id)){
                Cart item = cart.get(id);
                item.setProducts(pickProduct);
                item.setAmount(item.getAmount()+1);
                cart.put(id,item);
            }else {
                Cart item = new Cart();
                item.setProducts(pickProduct);
                item.setAmount(1);
                cart.put(id,item);
            }
        }

        return "myCart";
    }

    @GetMapping("/deletecart/{id}")
    public String removeItem(@ModelAttribute("cart") HashMap<Long,Cart> cart, @PathVariable Long id){
        if(cart==null){
            cart =  new HashMap<>();
        }
        if(cart.containsKey(id)){
            cart.remove(id);
        }
        return "myCart";
    }



}
