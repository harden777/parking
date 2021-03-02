package com.wwl.service;

import com.wwl.mapper.UserMapper;
import com.wwl.po.User;
import com.wwl.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     *登录
     * */
    public User loginUser(String username, String password) {
        User user=userMapper.loginUser(username, MD5Util.string2MD5(password));
        return user;
    }
    /**
     * 查询用户名是否存在
     * */
    public List<User> checkUsername(String username) {
        List<User>  users=userMapper.checkUsername(username);
       /* for (User user:users){
            System.out.println(user);
        }
*/
        return users;
    }
    /**
     * 工号验证，查看是否已存在
     * */
    public List<User> checkCode(String code) {
        List<User>  users=userMapper.checkCode(code);
        return users;
    }
    /**
     * 注册用户
     * */
    public int registerUser(User user) {
        user.setPassword(MD5Util.string2MD5(user.getPassword()));
        return userMapper.registerUser(user);
    }
    /**
     * 修改个人信息
     * */
    public Integer updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }

    /**
     *修改密码
     * */
    public Integer updatePassword(String code, String newPassword) {
        return userMapper.updatePassword(code,MD5Util.string2MD5(newPassword));
    }
    /**
     * 个人图片上传
     * */

    public void updateUserImg(String filePath, String code) {
      userMapper.updateUserImg(filePath,code);
    }
    /**
     * 条件查询用户，分页
     * */
    public List<User> findAll(Integer page, Integer limit, String code, String username, String name, Integer userid) {
        Integer pageSize=1;
        Integer pageNumber=5;
        if (!"".equals(page)||page!=null){
            pageSize=page;
        }
        if (!"".equals(limit)||limit==null){
            pageNumber=limit;
        }


        pageSize=(pageSize-1)*pageNumber;
        return userMapper.findAll(pageSize,pageNumber,code,username,name,userid);
    }
   /**
    * 条件查询用户总条数，分页
    * */
    public Integer findCount(String code, String username, String name, Integer userid) {
        return userMapper.findCount(code,username,name,userid);
    }
    /**
     * 修改状态
     * */
    public Integer editState(Integer id, Integer state) {
        return userMapper.editState(id,state);
    }

    /**
     * 修改权限
     * */
    public Integer editType(Integer id, Integer type) {
        return userMapper.editType(id,type);
    }
    /**
     * 删除用户
     * */
    public Integer deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    /**
    * 根据id查询员工
    * */
    public User findByid(int uid) {
        return userMapper.findByid(uid);
    }
}
