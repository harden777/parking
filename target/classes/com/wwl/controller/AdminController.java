package com.wwl.controller;


import com.wwl.po.Message;
import com.wwl.po.User;
import com.wwl.service.CarOrderService;
import com.wwl.service.MessageService;
import com.wwl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author wwl
 * @Date 2020/12/18 16:18
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private CarOrderService carOrderService;
    @Autowired
    private MessageService messageService;
/**
 * 错误页面跳转
 * */
        @RequestMapping("/error")
    public String error(){
        return "error";
    }

    /**
     * 首页查询
     * */
    @RequestMapping("/index")
    public String index(Model model) {
        //查询车库信息车位信息
        Map<String,Object> orders=carOrderService.systemState();
        //查询公告信息
        List<Message> messagesList=messageService.messageList();
        model.addAttribute("orders",orders);
        model.addAttribute("messages",messagesList);

        //map集合遍历
      /*  Iterator<Map.Entry<String, Object>> it = orders.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }*/

     /*   for (Message message:messagesList){
            System.out.println(message);

        }*/
        return "index";

    }

    /**
     * 人员管理页面跳转
     * */
    @RequestMapping("/employee")
    public String employee( ){

            return "user/employee";
    }
    /**
     * 用户列表
     * */
    @RequestMapping("/employDate")
    @ResponseBody
    public Map<String,Object> employee(Integer page, Integer limit, String code, String username, String name, HttpSession session){
        //获取当前登录用户，当前登录用户不显示在查询列表
        User user= (User) session.getAttribute("USER_LOGIN");
        Integer userid=user.getId();
        //条件查询用户，分页
        List<User> users=userService.findAll(page,limit,code,username,name,userid);

        //条件查询用户总条数，分页
        Integer count=userService.findCount(code,username,name,userid);
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);//layui状态码
        map.put("data",users);
        map.put("count",count);

        return map;
    }
/**
     * 修改状态
     * */
    @RequestMapping("/editstate")
    @ResponseBody
    public Map<String,Object> editState(Integer id,Integer state){
        Map<String,Object> map=new HashMap<>();

        if (state==0){
            state=1;
        }else {
            state=0;
        }
        Integer count=userService.editState(id,state);
        if (count>=1){
            map.put("code",1);
            map.put("msg","修改成功");

        }else {

            map.put("msg","修改失败");
        }

        return map;
    }

    /**
     * 修改权限
     * */
    @RequestMapping("/edittype")
    @ResponseBody
    public Map<String,Object> editType(Integer id,Integer type){
        Map<String,Object> map=new HashMap<>();

        if (type==0){
            type=1;
        }else {
            type=0;
        }
        Integer count=userService.editType(id,type);
        if (count>=1){
            map.put("code",1);
            map.put("msg","修改成功");

        }else {

            map.put("msg","修改失败");
        }

        return map;
    }

    /**
    * 删除用户
    * */
    @RequestMapping("/deleteuser")
    @ResponseBody
    public Map<String,Object> deleteUser(Integer id){
        Map<String,Object> map=new HashMap<>();

        Integer count=userService.deleteUser(id);
        if (count>=1){
            map.put("code",1);
            map.put("msg","删除成功");

        }else {

            map.put("msg","删除失败");
        }

        return map;
    }

    /**
     * 修改员工密码
    * */
    @RequestMapping("/editpassword")
    @ResponseBody
    public Integer editPassword(String upcode,String uppassword){
        userService.updatePassword(upcode,uppassword);
        return userService.updatePassword(upcode,uppassword);
    }
}


