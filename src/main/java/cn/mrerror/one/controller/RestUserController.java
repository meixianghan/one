package cn.mrerror.one.controller;

import cn.mrerror.one.annotation.Token;
import cn.mrerror.one.entity.User;
import cn.mrerror.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestUserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/{username}/{password}", method = RequestMethod.POST)//create
    @ResponseBody
    public Object addUser(@PathVariable String username, @PathVariable String password) {
        User user = new User();
        user.setAccount(username);
        user.setPassword(password);
        userService.insert(user);
        return this.get();

    }

    @RequestMapping(value="/show",method=RequestMethod.GET)//select
    @Token(apply = true)
    public ModelAndView show(){
       return new ModelAndView("restapi");
    }

    @RequestMapping(value="/users",method=RequestMethod.GET)//select
    @ResponseBody
    @Token(apply = true)
    public Object get(){
        List<User> userList = userService.select();
        return userList;
    }

    @RequestMapping(value="/users/{id}",method=RequestMethod.GET)//select
    @ResponseBody
    @Token(apply = true)
    public Object get(@PathVariable("id") Integer id){
        return userService.selectById(new User(id));
    }

    @RequestMapping(value="/users/{id}/{password}",method=RequestMethod.PUT)//update
    @ResponseBody
    @Token(consumer = true)
    public Object put(@PathVariable("id") Integer id,@PathVariable("password") String password){
        userService.update(new User(id,"",password));
        return this.get();
    }

    @RequestMapping(value="/users/{id}",method=RequestMethod.DELETE)//delete
    @ResponseBody
    @Token(consumer = true)
    public Object delete(@PathVariable("id") Integer id){
        userService.delete(new User(id));
        return this.get();
    }

}