package cn.mrerror.one.controller;

import cn.mrerror.one.entity.Product;
import cn.mrerror.one.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/product",method = RequestMethod.GET)
    @ResponseBody
    public Object selectProduct(){
        return productService.selectProduct();
    }

    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object selectProductBy(@PathVariable int id){
        Product product = new Product();
                product.setId(id);
        return productService.selectProductBy(product);
    }

    @RequestMapping(value="/product/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteProduct(@PathVariable int id){
        return productService.deleteProduct(id);
    }

    @RequestMapping(value="/product",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public boolean insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    @RequestMapping(value = "/product",method = RequestMethod.PUT,consumes = "application/json")
    @ResponseBody
    public boolean updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }
}
