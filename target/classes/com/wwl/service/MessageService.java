package com.wwl.service;

import com.wwl.mapper.MessageMapper;
import com.wwl.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wwl
 * @Date 2020/12/18 23:01
 * @Version 1.0
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询公告信息
     * */
    public List<Message> messageList() {
        return messageMapper.messageList();
    }

    /**
     *删除公告
     * */
    public Integer delMessage(Integer id) {
        return messageMapper.delMessage(id);
    }

    /**
     * 添加公告
     * */
    public Integer addMessage(Message message) {
        return messageMapper.addMessage(message);
    }
}
