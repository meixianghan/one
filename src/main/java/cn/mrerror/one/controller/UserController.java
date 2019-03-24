package cn.mrerror.one.controller;

import cn.mrerror.one.entity.Department;
import cn.mrerror.one.entity.User;
import cn.mrerror.one.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController implements ServletContextAware {

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        String ctx = servletContext.getContextPath();
        servletContext.setAttribute("ctx", ctx);
    }

    @Autowired
    UserService userService;

    @InitBinder("buyer")
    public void initBuyerBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("buyer.");
    }

    @InitBinder("seller")
    public void initSellerBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("seller.");
    }

    @RequestMapping("/pw")
    public void pw(String name,PrintWriter pw){
        pw.write("hello,"+name);
    }
    @ResponseBody
    @RequestMapping("/login")
    public User login(User user){
        System.out.println("请求登录的用户为:"+user.toString());
        user = userService.doLogin(user);
        return user;
    }

    @RequestMapping("/loginjsp")
    public String loginjsp(){
        return "/WEB-INF/jsp/login";
    }

    /**
     *{"account":"1111","password":"2222"}
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/loginJson",consumes="application/json")
    public Object loginJson(@RequestBody User user){
        return user;
    }

    @ResponseBody
    @RequestMapping(value="/userSession",consumes="application/json")
    public Object userSession(@RequestBody User user,HttpSession session){
        if(session.getAttribute("user")!=null){
            return session.getAttribute("user");
        }else{
            session.setAttribute("user",user);
        }
        return user;
    }

    @ResponseBody
    @RequestMapping(value="/getSessionUser")
    public Object getSessionUser(HttpSession session){
        return session.getAttribute("user");
    }

    @ResponseBody
    @RequestMapping(value="/insertUserMap",consumes="application/json")
    public Object insertUserMap(@RequestBody Map<String, String> users){
        return users;
    }

    /**
     * [{"account":"1111","password":"2222"}]
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertUsers",consumes="application/json")
    public Object insertUsers(@RequestBody List<User> users){
        return users;
    }

    @ResponseBody
    @RequestMapping(value="/insertUsersArrays",consumes="application/json")
    public Object insertUsersArrays(@RequestBody User[] users){
        return users;
    }

    @RequestMapping(value = "/callback")
    @ResponseBody
    public Object callback(HttpServletRequest httpServletRequest) {
        final Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        return parameterMap;
    }

    @RequestMapping(value = {"/modifyGet.do","/modifyGet1.do"},
                    method={RequestMethod.POST, RequestMethod.GET},
                    consumes={"application/json"},
                    params={"username=zhangsan","password=123"},
                    headers="a=1")
    @ResponseBody
    public Object addEmpGet(@RequestBody JSONObject jsonObject)throws Exception {
        JSONObject responseObj = new JSONObject();
                   responseObj.put("id", jsonObject.getIntValue("id"));
        return responseObj ;
    }

    @ResponseBody
    @RequestMapping("/buy")
    public Object buy(@ModelAttribute("buyer")User buyer,@ModelAttribute("seller")User seller){
        Map<String,Object> out = new HashMap<String,Object>();
        out.put("buyer",buyer);
        out.put("seller",seller);
        return out;
    }

    @RequestMapping("/userRR")
    public void userRR(User user, HttpServletRequest request, HttpServletResponse response){
        System.out.println(user);
        System.out.println(request);
        System.out.println(response);
    }

    @ResponseBody
    @RequestMapping("/param")
    //http://localhost:8080/ssm?account=zhangsan&password=123
    public Object param(String account,String password){
        Map<String,Object> out = new HashMap<String,Object>();
                           out.put("account",account);
                           out.put("password",password);
        return out;
    }


    @ResponseBody
    @RequestMapping(value = "/reqParam",method = {RequestMethod.GET,RequestMethod.POST})
    public Object reqParam(@RequestParam(required = false,name = "username",defaultValue = "lisi") String account,
                           @RequestParam(required = true) String password,String platform){
        Map<String,Object> out = new HashMap<String,Object>();
        out.put("account",account);
        out.put("password",password);
        out.put("platform",platform);
        return out;
    }

    @RequestMapping("/view")
    public ModelAndView view(){
        List<User>  userList = userService.select();
        ModelAndView modelAndView = new ModelAndView();
                     modelAndView.setViewName("view");
                     modelAndView.getModel().put("userList",userList);
        return modelAndView;
    }

    @RequestMapping("/viewRedirect")
    public ModelAndView viewRedirect(){
//        int x = 1/0;
        Object object = null;
               object.hashCode();

        ModelAndView modelAndView = new ModelAndView();
                     modelAndView.setViewName("redirect:/user/view");
        return modelAndView;
    }

    @RequestMapping("/freemark")
    public ModelAndView freemark(){
        List<User>  userList = userService.select();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("show");
        modelAndView.getModel().put("userList",userList);
        return modelAndView;
    }

    @RequestMapping(value = "/thymeleaf")
    public String query(String name, ModelMap model) {
        model.addAttribute("name", "张三");
        model.addAttribute("query", name);
        model.addAttribute("submit", "提交");
        return "hello";
    }

    @RequestMapping(value = "/velocity")
    public String velocity(ModelMap model) {
        List<Department> departmentList = new ArrayList<Department>();
                         departmentList.add(new Department(1,"技术部"));
                         departmentList.add(new Department(2,"研发部"));
                         departmentList.add(new Department(3,"工程部"));
                         departmentList.add(new Department(4,"科研部"));
        model.addAttribute("departments",departmentList);
        return "index";
    }

    @ResponseBody
    @RequestMapping("/select")
    public List<User> select(){
        List<User>  userList = userService.select();
        return userList;
    }

    @RequestMapping("/insert")
    public String insert(Model model){
        User user = new User();
             user.setAccount("zhaoliu");
             user.setPassword("zhaoliu");
        if(userService.insert(user)==1){
            return "forward:/user/select";
        }
        model.addAttribute("errorTip","插入失败");
        return "error";
    }

    @RequestMapping("/redirect")
    public String redirect(){
        return "redirect:http://www.baidu.com";
    }

    /**
     * 获取RedirectAttribute 的数据
     * @param name 用户名
     * @param age 年龄
     * @param price 价格
     * @return 返回接收的结果
     */
    @ResponseBody
    @RequestMapping("/getRedirectAttr")
    public Object getRedirectAttr(@ModelAttribute("name") String name,@ModelAttribute("age") int age,@ModelAttribute("price") float price){
        Map<String,Object> result = new HashMap<>();
                           result.put("name",name);
                           result.put("age",age);
                           result.put("price",price);
        return result;
    }

    /**
     * 重定向,内部携带数据
     * 避免从定向从地址加入？参数
     * @param redirectAttributes 重定向数据携带对象
     * @return 重定向地址
     */
    @RequestMapping("/redirectAttr")
    public String redirectAttr(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("name","zhangsan");
        redirectAttributes.addFlashAttribute("age",30);
        redirectAttributes.addFlashAttribute("price",2.33);
        return "redirect:/user/getRedirectAttr";
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(){
        User user = new User();
             user.setId(1);
             user.setPassword("zhangans1");
        return userService.update(user);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(){
        User user = new User();
             user.setId(2);
        return userService.delete(user);
    }


    @ResponseBody
    @RequestMapping("/showUpload")
    public Object showUpload(HttpServletRequest req){
        List<String> fileList = new ArrayList<>();
        for (File file : new File(req.getSession().getServletContext().getRealPath("/") + "upload/").listFiles()) {
            fileList.add(file.getName());
        }
        return fileList;
    }

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public String upload(HttpServletRequest req) throws Exception{
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)req;
        mreq.getFileNames().forEachRemaining(inputName->{
            try {
                MultipartFile file = mreq.getFile(inputName);
                String fileName = file.getOriginalFilename();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                FileOutputStream fos = new FileOutputStream(req.getSession().getServletContext().getRealPath("/") +
                        "upload/" + sdf.format(new Date()) + fileName.substring(fileName.lastIndexOf('.')));
                fos.write(file.getBytes());
                fos.flush();
                fos.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        return "forward:/user/showUpload";
    }


}