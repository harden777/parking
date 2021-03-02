package com.wwl.mapper;

import com.wwl.po.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    /**
     *登录
     * */
    @Select("select * from user where username=#{username} and password = #{password} and state=1 ")
    User loginUser(@Param("username") String username, @Param("password") String string2MD5);
    /**
     * 查询用户名是否存在
     * */
    @Select("select * from user where username=#{username}")
    List<User> checkUsername(@Param("username") String username);
    /**
     * 工号验证，查看是否已存在
     * */
    @Select("select * from user where code=#{code}")
    List<User> checkCode(@Param("code")String code);
    /**
     * 注册用户
     * */
    @Insert("insert into user values(null,#{code},#{username},#{password},#{name},#{sex},#{email},#{phone},#{say},#{state},#{type},#{headImg})")
    int registerUser(User user);
    /**
     * 个人修改个人信息
     * */
    @Update("update user set name=#{name},email=#{email},phone=#{phone},say=#{say} where code=#{code}")
    Integer updateUserInfo(User user);

    @Update("update user set password=#{password} where code=#{code}")
    Integer updatePassword(@Param("code") String code, @Param("password") String newPassword);
    /**
     * 个人图片上传
     * */
    @Update("update user set headImg=#{filePath} where code=#{code}")
    void updateUserImg(String filePath,String code);
    /**
     * 根据id查询用户
    * */
    @Select("select * from user where id=#{uid}")
    User findById(@Param("uid") Integer uid);

    /**
     * 查询所有用户+条件查询
     * */
    @Select("<script>" +
            "select * from user " +
            "<where>" +
            "   <if test='username!=null and username!=&quot;&quot; '>" +
            "   and username like CONCAT('%', #{username}, '%')" +
            "   </if>" +
            "   <if test='name!=null and name!=&quot;&quot; '>" +
            "   and name like CONCAT('%', #{name}, '%')" +
            "   </if>" +
            "   <if test='code!=null and code!=&quot;&quot; '>" +
            "   and code=#{code}" +
            "   </if>" +
            "and id  &lt;&gt; #{userid}"+
            "</where>" +
            "limit #{page},#{limit}"+
            "</script>")
    List<User> findAll(Integer page, Integer limit, String code, String username, String name, Integer userid);

    /**
     * 查询所有用户数量+条件查询
     * */
    @Select("<script>" +
            "select count(*) from user " +
            "<where>" +
            "   <if test='username!=null and username!=&quot;&quot; '>" +
            "   and username like CONCAT('%', #{username}, '%')" +
            "   </if>" +
            "   <if test='name!=null and name!=&quot;&quot; '>" +
            "   and name like CONCAT('%', #{name}, '%')" +
            "   </if>" +
            "   <if test='code!=null and code!=&quot;&quot; '>" +
            "   and code=#{code}" +
            "   </if>" +
            "and id  &lt;&gt; #{userid}"+
            "</where>" +
            "</script>")
    Integer findCount(String code, String username, String name, Integer userid);

    /**
     * 修改状态
     * */
    @Update("update user set state=#{state} where id=#{id}")
    Integer editState(Integer id, Integer state);

    /**
     * 修改权限
     * */
    @Update("update user set type=#{type} where id=#{id}")
    Integer editType(Integer id, Integer type);

    /**
     * 删除用户
     * */
    @Delete("delete from user where id=#{id}")
    Integer deleteUser(Integer id);

    /**
     * 根据id查询用户
     * */
    @Select("select * from user where id=#{id}")
    User findByid(@Param("id") int uid);
}
