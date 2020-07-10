package codegym.controller;


import codegym.model.Cart;
import codegym.model.Product;
import codegym.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("cart")
public class CartController {

    @Autowired
    ProductService productService;

    @ModelAttribute("cart")
    public HashMap<Long,Cart> myCart(){
        return new HashMap<>();
    }

    @GetMapping("/cart")
    public ModelAndView showCart(@ModelAttribute("cart")HashMap<Long,Cart> cart){
        if(cart==null){
            cart=new HashMap<>();
        }

        ModelAndView mv = new ModelAndView("myCart");
        mv.addObject("totalPrice",this.totalPrice(cart));
        return mv;
    }


    @GetMapping("/addcart/{id}")
    public ModelAndView addCart(@ModelAttribute("cart")HashMap<Long,Cart> cart, @PathVariable Long id){
        cart = ensureCartNotNull(cart, id);
        insertProductToCart(cart, id);
        ModelAndView mv = new ModelAndView("myCart");
        mv.addObject("totalPrice",this.totalPrice(cart));

        return mv;
    }

    private HashMap<Long, Cart> ensureCartNotNull(HashMap<Long, Cart> cart, Long id) {
        if(cart==null){
            cart= new HashMap<>();
        }
        return cart;
    }

    public void insertProductToCart(HashMap<Long, Cart> cart, Long id) {
        Product pickProduct = productService.findOne(id);
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
    }

    @GetMapping("/deletecart/{id}")
    public ModelAndView removeItem(@ModelAttribute("cart") HashMap<Long,Cart> cart, @PathVariable Long id){
        if(cart==null){
            cart =  new HashMap<>();
        }
        if(cart.containsKey(id)){
            cart.remove(id);
        }
        ModelAndView mv = new ModelAndView("myCart");
        mv.addObject("totalPrice",this.totalPrice(cart));
        return mv;
    }

    public int totalPrice(HashMap<Long,Cart> mycart){
        int total = 0;
        for (Map.Entry<Long,Cart> list:mycart.entrySet()) {
            total+= list.getValue().getProducts().getPrice()*list.getValue().getAmount();
        }
        return total;
    }

}
