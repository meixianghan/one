package cn.mrerror.one.controller;

import cn.mrerror.one.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/api/")
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object selectCategory(@PathVariable int id){
        return categoryService.selectCategory(id);
    }

}