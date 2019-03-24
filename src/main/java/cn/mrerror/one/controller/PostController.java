package cn.mrerror.one.controller;

import cn.mrerror.one.entity.Post;
import cn.mrerror.one.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)//select
    @ResponseBody
    public Object posts() {
        return postService.selectPosts(new Post());
    }

    @RequestMapping(value = "/all/{page}/{size}", method = RequestMethod.GET)//select
    @ResponseBody
    public Object post(@PathVariable int page,@PathVariable int size) {
        return postService.selectPosts(page,size,new Post(1)).getList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)//create
    @ResponseBody
    public Object get() {
        List<Post> postList = new ArrayList<>();
                   postList.add(new Post("亚马逊","亚马逊森林",2));
                   postList.add(new Post("泰山","泰山森林",2));
                   postList.add(new Post("衡山","衡山森林",2));
                   postList.add(new Post("珠穆朗玛峰","雪山森林",2));
                   postList.add(new Post("华山","华山森林",2));
        return postService.insertPosts(postList);
    }
}