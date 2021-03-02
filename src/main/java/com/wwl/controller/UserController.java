package com.wwl.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.wwl.po.User;
import com.wwl.service.UserService;
import com.wwl.utils.AliCloud;
import com.wwl.utils.FileDelete;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller

//进入登录页面
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String tologin() {


        return "login";
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<?, ?> login(String username, String password, HttpSession session) {

        Map<String, Object> result = new HashMap<>();
        User user = userService.loginUser(username, password);
        if (user != null) {
            session.setAttribute("USER_LOGIN", user);
            result.put("msg", "登录成功   loading···");
            result.put("status", "success");
        } else {
            result.put("msg", "登录失败,用户名或密码错误");

        }
        return result;
    }

    /**
     * 查询用户名是否存在
     */
    @RequestMapping("/checkUsername")
    @ResponseBody
    public boolean checkUsername(String username, Model model) {
        System.out.println("123");
        boolean flag;
        List<User> users = userService.checkUsername(username);
        System.out.println(users != null && !users.isEmpty());
        return users != null && !users.isEmpty()? true : false;
    }

    @RequestMapping("/checkUsernameLogin")
    public boolean checkUsernameLogin(String username, Model model) {

        List<User> users = userService.checkUsername(username);
        if (users != null && !users.isEmpty()){
            return true;
        }
        return false;

    }

    /**
     * 工号验证，查看是否已存在
     */
    @RequestMapping("/checkCode")
    @ResponseBody
    public boolean checkCode(String code, Model model) {

        boolean flag;
        List<User> users = userService.checkCode(code);

        return users != null && !users.isEmpty() ? false : true;
    }

    /**
     * 注册用户
     */
    @RequestMapping("/register")
    @ResponseBody
    public Integer register(String username, String code, String name, String sex, String email, String phone, String say) {
        User user = new User();
        user.setCode(code);
        user.setUsername(username);
        user.setPassword(username);
        user.setName(name);
        user.setSex(Integer.parseInt(sex));
        user.setEmail(email);
        user.setPhone(phone);
        user.setHeadImg("https://parking777.oss-cn-heyuan.aliyuncs.com/user.png");
        if (say != null && !say.equals("")) {
            user.setSay(say);
        } else {
            user.setSay("网络一线牵珍惜这份缘！");
        }
        user.setState(1);
        user.setType(0);

        return userService.registerUser(user);
    }



    /**
     * 点击个人设置跳转
     */
    @GetMapping("/userSetting")
    public String setting() {
        return "user/userSetting";
    }

    /**
     * 个人修改个人信息
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public Integer updateUserInfo(String name, String email, String phone, String say, HttpSession session) {
        User user = (User) session.getAttribute("USER_LOGIN");
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSay(say);
        int count = userService.updateUserInfo(user);
        session.setAttribute("USER_LOGIN", user);

        return count;
    }

    /**
     * 密码验证
     */
    @RequestMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password, HttpSession session) {

        User user = (User) session.getAttribute("USER_LOGIN");
        User u = userService.loginUser(user.getUsername(), password);
        if (u != null) {
            return true;
        }
        return false;
    }
    /**
    *修改密码
     * */
    @RequestMapping("/updatePassword")
    public String updatePassword(String newPassword, Model model, HttpSession session) {
        User user = (User) session.getAttribute("USER_LOGIN");
        Integer count = userService.updatePassword(user.getCode(), newPassword);


        if (count >= 1) {
           session.invalidate();
            model.addAttribute("msg", "密码修改成功，请重新登录！");

        return "login";
      //  return "redirect:/user/toLogin";
        }else {
            model.addAttribute("msg", "密码修改失败！");
            return "/user/userSetting";
        }

    }

        /**
         * 退出
         * */
        @RequestMapping("/logout")
        public String logout(HttpSession session){
            session.invalidate();
            return "redirect:/user/toLogin";
        }
        /**
         * 个人图片上传
        * */
        @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
        @ResponseBody
        public boolean fileImgUpload(@RequestParam("uploadfile") MultipartFile uploadfile,
                                    HttpServletRequest request, HttpSession session){
           //上传到本地
           /*
           判断所上传文件是否存在
           if (!uploadfile.isEmpty()) {

                User user = (User) session.getAttribute("USER_LOGIN");
                // 获取上传文件的原始名称
                String originalFilename = uploadfile.getOriginalFilename();
                String name=originalFilename.substring(originalFilename.indexOf("."));
                String newname=user.getCode()+name;
                // 设置上传文件的保存地址目录
              上传到tomcat路径
                String dirPath = request.getSession().getServletContext().getRealPath("/")+"images/userimage/";
                System.out.println(dirPath+"dirpath");
                File file = new File(dirPath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                // 使用UUID重新命名上传的文件名称(上传人_uuid_原始文件名称)
                String newFilename = UUID.randomUUID().toString().substring(0, 7) + "_" + newname;
                String filePath =  "/images/userimage/"+newFilename;

                String oldImg = user.getHeadImg().replace("/images/userimage/", "");
               // System.out.println(oldImg+"oid");
                userService.updateUserImg(filePath, user.getCode());
                user.setHeadImg(filePath);

                session.setAttribute("USER_LOGIN", user);
                try {
                    //获取项目路径
                    String path = ResourceUtils.getURL("").getPath();
                    //获取到target路径
                  // String path = ResourceUtils.getURL("classpath:").getPath()+"static/images/userimage/";
                   // System.out.println(path);
                    String realPath2 =  path.replace('/', '\\').substring(1,path.length());

                    String paths=realPath2+"/src\\main\\resources\\static\\images\\userimage\\";
                    File file2=new File(paths+newFilename);
                    //上传到本地目录

                   if (!file2.getParentFile().exists()) {
                        file2.getParentFile().mkdirs();

                    }
                    // 使用MultipartFile接口的方法完成文件上传到指定位置
                   //uploadfile.transferTo(upload2);
                    uploadfile.transferTo(file2);
                   //删除原先文件
                    if (!oldImg.equals("admin.png")&&!oldImg.equals("user.png")){
                       FileDelete.deleteFile(paths+oldImg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                // 跳转到成功页面
                return true;
            } else {
                return false;
            }*/
           //上传到阿里云OSS服务器
            if (!uploadfile.isEmpty()) {
                // Endpoint以杭州为例，其它Region请按实际情况填写。
                String endpoint = AliCloud.Endpoint;
                // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
                String accessKeyId = AliCloud.AccessKey_ID;
                String accessKeySecret = AliCloud.AccessKey_Secret;
                String bucketName = AliCloud.BucketName;
                OSS ossClient = null;
                try {
                    // 创建OSSClient实例。
                    ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

                    // 获取上传文件流。
                    InputStream inputStream = uploadfile.getInputStream();
                    // 获取上传文件的原始名称
                    String originalFilename = uploadfile.getOriginalFilename();

                    //获取文件后缀名
                    String name=originalFilename.substring(originalFilename.indexOf("."));
                    //获取原图片名称并删除
                    User user = (User) session.getAttribute("USER_LOGIN");
                    String headImg=user.getHeadImg();
                    String[] s=headImg.split("\\.com/");
                    String oldName=s[1];
                    //在文件名称中拼接唯一的值，避免文件重名
                    String newName = UUID.randomUUID().toString().substring(0, 7) + "_" + user.getCode()+name;
                    //文件按照日期分类
                    //获取当前日期
                    String time = new DateTime().toString("yyyy/MM/dd");
                    String newFileName=time+ "/" + newName;

                    //调用方法上传文件//1.bucket名称   2.文件名称    3.文件输入流
                    ossClient.putObject(bucketName, newFileName, inputStream);

                    OSS os=new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                    //删除原图片
                    if (!oldName.equals("admin.png")&&!oldName.equals("user.png")){
                        os.deleteObject(bucketName, oldName);
                        // 关闭OSSClient。
                        os.shutdown();
                    }
                    // 关闭OSSClient。
                    ossClient.shutdown();
                    //把上传到阿里云的路径拼接,修改数据库存放url
                    String url = "https://" + bucketName + "." + endpoint + "/" + newFileName;
                    userService.updateUserImg(url, user.getCode());
                    user.setHeadImg(url);
                    session.setAttribute("USER_LOGIN", user);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }



            return false;
        }

}

