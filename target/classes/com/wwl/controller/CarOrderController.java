package com.wwl.controller;

import com.wwl.po.CarOrder;
import com.wwl.po.CarSpace;
import com.wwl.po.CarStation;
import com.wwl.po.User;
import com.wwl.service.CarOrderService;
import com.wwl.service.CarSpaceService;
import com.wwl.service.CarStationService;
import com.wwl.utils.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;


import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author wwl
 * @Date 2020/12/18 22:52
 * @Version 1.0
 */

@Controller
@RequestMapping("/order")
public class CarOrderController {
    @Autowired
    private CarOrderService carorderService;
    @Autowired
    private CarSpaceService carSpaceService;
    @Autowired
    private CarStationService carStationService;
    /**
     * 今日工作
     * */
    @RequestMapping("/today")
    public String today(String time, Model model){

        List<CarOrder> orders=carorderService.findByTime(time);
        double cost = 0.0;
        for (CarOrder c : orders) {
            cost += c.getCost();
        }
        //返回查询时间
        String backTime;
        if (time==null||time.equals("")){
            backTime= Time.getDay();
        }else {
            backTime=time;
        }
        model.addAttribute("orders", orders);
        model.addAttribute("cost", cost);
        model.addAttribute("count", orders.size());
        model.addAttribute("backTime", backTime);
        return "order/today";
    }

    /**
     * 车位查询
     * */
    @RequestMapping("/searchcarnumber")
    @ResponseBody
    public Map<String,Object> searchCarNumber(String selectId,String carNumber){
        return carorderService.searchCarNumber(selectId,carNumber);
    }

    /**
     * 查询车位名称
     * */
    @RequestMapping("/searchCaspace")
    @ResponseBody
    public CarSpace findSpaceCode(String s_name){

        return carSpaceService.findSpaceName(s_name);
    }

    /**
     * 查询order
     * */
    @RequestMapping("/findCarorder")
    @ResponseBody
    public Map<String,Object> findCarorder(String province,String carNumber){

        Map<String,Object> map=carorderService.findCarorder(province,carNumber);
        return map;
    }

    /**
     * 车辆出库
     * */
    @RequestMapping("/OutCar")
    @ResponseBody
    public Integer OutCar(Integer o_id, Integer s_id, double time, double cost){
        CarOrder carOrder=new CarOrder();
        carOrder.setId(o_id);
        carOrder.setSid(s_id);
        carOrder.setCost(cost);
        carOrder.setTime(time);
        carOrder.setEndTime(new Timestamp(new Date().getTime()));
        carOrder.setState(1);
        return carorderService.OutCar(carOrder);
    }

    /**
     * 出入记录
     * */
    @RequestMapping("/records")
    private String records(){

        return "order/record";
    }
    /**
     * 订单分页
     * */
    @RequestMapping("/recordList")
    @ResponseBody
    private Map<String,Object> recordList(Integer page,Integer limit){
        return carorderService.recordList(page,limit);

    }

    /**
     * 删除订单
     * */
    @RequestMapping("/delOrder")
    @ResponseBody
    public Integer delOrder(Integer id){

        return carorderService.delOrder(id);
    }
    /**
     * 车位查询
     * */
    @RequestMapping(value = "/carInto",method = RequestMethod.GET)
    public String carInto(Model model){
        List<CarStation> carStations =carStationService.findCarStationList();
        CarStation carStation=carStationService.findCarStations(carStations.get(0).getC_id());
        model.addAttribute("carStations",carStations);
        model.addAttribute("carStation",carStation);
        model.addAttribute("c_id",carStations.get(0).getC_id());//用于选项框被选中
        return "order/carInto";
    }
    /**
     * 车位查询
     * */
    @RequestMapping(value = "/carInto",method = RequestMethod.POST)
    public String carInto(Integer cid ,Model model){
        List<CarStation> carStations =carStationService.findCarStationList();
        CarStation carStation=carStationService.findCarStations(cid);
        model.addAttribute("carStations",carStations);
        model.addAttribute("carStation",carStation);
        model.addAttribute("c_id",cid);
        return "order/carInto";
    }

    /**
     * 入库车辆查询
     * */
    @RequestMapping("/orderInfo")
    @ResponseBody
    public Map<String,Object> orderInfo(Integer sid){

        return carorderService.orderInfo(sid);
    }

    /**
     * 验证车牌是否未出库
     * */
    @RequestMapping("/checkCarNumber")
    @ResponseBody
    public boolean checkCarNumber(String province,String carNumber){
        CarOrder carOrder=carorderService.checkCarNumber(province,carNumber);
        boolean flag=true;
        if (carOrder!=null&&!carOrder.equals("")){
            flag=false;
        }
        return flag;
    }
    /**
     * 车辆入库
     * */
    @RequestMapping("/parkingInto")
    @ResponseBody
    public Integer parkingInto(Integer sid, Integer cid, String province, String carNumber, String customerName, String customerPhone, Integer is, HttpSession session){

        User user= (User) session.getAttribute("USER_LOGIN");
        CarOrder carOrder=new CarOrder();
        carOrder.setProvince(province);
        carOrder.setCarNumber(carNumber);
        carOrder.setCustomerName(customerName);
        carOrder.setCustomerPhone(customerPhone);
        carOrder.setCost(0.0);
        carOrder.setTime(0.0);
        carOrder.setCid(cid);
        carOrder.setSid(sid);
        carOrder.setUid(user.getId());
        carOrder.setStartTime(new Timestamp(new Date().getTime()));
        carOrder.setState(0);
        carOrder.setIs(is);
        Integer count=carorderService.parkingInto(carOrder);
        return count;
    }
    /**
     * 车牌识别
     * */
    @RequestMapping("/OCRNumberParkIn")
    @ResponseBody
    public  Map<String, Object> OCRNumberParkIn(String Base64Image){
        //所有图片均需要base64编码、去掉编码头后再进行urlencode。
        String bImg = Base64Image.substring(23);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(bImg);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
                String PriNum=carorderService.OCRNumberParkIn(b);
               if (PriNum!=null&&!PriNum.equals("")){
                   String province = PriNum.substring(0, 1);
                   String number = PriNum.substring(1);
                   Map<String, Object> carNumber = new HashMap<String, Object>();
                   carNumber.put("province", province);
                   carNumber.put("number", number);
                   return carNumber;
               }
                return null;
            }

        } catch (Exception e) {
            return null;
        }
        return null;

    }
}
