package cn.mrerror.one.controller;

import cn.mrerror.one.entity.Blog;
import cn.mrerror.one.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blogs", method = RequestMethod.GET)//select
    @ResponseBody
    public Object get() {
        Blog blog = blogService.selectBlog(new Blog(1));
        return blog;
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)//select
    @ResponseBody
    public Object author() {
        List<Blog> blog = blogService.selectBlogAuthor(new Blog(1));
        return blog;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)//select
    @ResponseBody
    public Object authors() {
        Blog blog = blogService.selectBlogDoubleAuthor(new Blog(1));
        return blog;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)//select
    @ResponseBody
    public Object posts() {
        Blog blog = blogService.selectBlogPostCollection(new Blog(1));
        return blog;
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)//select
    @ResponseBody
    public Object post() {
        Blog blog = blogService.selectBlogPosts(new Blog(1));
        return blog;
    }




}