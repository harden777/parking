package com.wwl.mapper;

import com.wwl.po.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wwl
 * @Date 2020/12/18 22:57
 * @Version 1.0
 */
@Mapper
@Repository
public interface MessageMapper {
    /**
     * 查询公告信息
     * */
    @Select("select * from message")
    @Results(id = "MessageMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "creatTime",property = "creatTime"),
            @Result(property = "user",column = "uid",one = @One(select = "com.wwl.mapper.UserMapper.findById",fetchType = FetchType.EAGER))

    })
    List<Message> messageList();

    /**
     *删除公告
     * */
    @Delete("delete from message where id=#{id}")
    Integer delMessage(Integer id);

    /**
     * 添加公告
     * */
    @Insert("insert into message values(null,#{title},#{content},#{creatTime},#{uid})")
    Integer addMessage(Message message);
}
