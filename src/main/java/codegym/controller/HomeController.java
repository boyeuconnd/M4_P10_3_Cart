package codegym.controller;


import codegym.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "")
    public String getIndex(){
        return "index";
    }

    @ModelAttribute
    public void productList(Model model){
        model.addAttribute("products", productRepository.findAll());

    }

    @GetMapping("product/{id}")
    public ModelAndView getDetail(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("productDetail");
        mv.addObject("product", productRepository.findOne(id));
        return mv;
    }
}
