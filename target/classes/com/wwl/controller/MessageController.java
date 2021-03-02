package com.wwl.controller;

import com.wwl.po.Message;
import com.wwl.po.User;
import com.wwl.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Author wwl
 * @Date 2020/12/18 22:54
 * @Version 1.0
 */
@Controller
@RequestMapping("/msg")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 查询公告信息
     * */
    @RequestMapping("/messages")
    public String messages(Model model){
        List<Message> messagesList=messageService.messageList();
        model.addAttribute("messages",messagesList);
        return "/msg/message";
    }

    /**
     *删除公告
     * */
    @RequestMapping("/delMessage")
    @ResponseBody
    public Integer delMessage(Integer id){
        return messageService.delMessage(id);
    }

    /**
     * 添加公告
     * */
    @RequestMapping("/addMessage")
    @ResponseBody
    public Integer addMessage(String content, String title, HttpSession session){
        User user = (User) session.getAttribute("USER_LOGIN");
        Message message=new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setUid(user.getId());
        message.setCreatTime(new Timestamp(new Date().getTime()));

        return messageService.addMessage(message);
    }

}
