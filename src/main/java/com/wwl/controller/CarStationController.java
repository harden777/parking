package com.wwl.controller;

import com.wwl.po.CarStation;
import com.wwl.po.User;
import com.wwl.service.CarStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wwl
 * @Date 2020/12/18 22:53
 * @Version 1.0
 */
@Controller
@RequestMapping("/car")
public class CarStationController {
    @Autowired
    private CarStationService carStationService;

    /**
     * 车库信息页面跳转
     * */
    @RequestMapping("/carstation")
    public String carstation(){

        return "car/carstationList";
    }

    /**
     * 查询车库，分页
     * */
    @RequestMapping("/carstationList")
    @ResponseBody
    public  Map<String,Object> carstationList(Integer page, Integer limit){
        //查询车库，分页
        List<CarStation> carStations=carStationService.carstationList(page,limit);
        //查询车库总条数，分页
       Integer count=carStationService.findCount();
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);//layui状态码
        map.put("data",carStations);
        map.put("count",count);

        return  map;
    }

    /**
     * 添加车库
     * */
    @RequestMapping("/addCarStation")
    @ResponseBody
    public Integer addCarStation(String name, String location, String description, Integer total, Double price, Double time){
        CarStation carStation=new CarStation();
        carStation.setC_name(name);
        carStation.setC_location(location);
        carStation.setC_description(description);
        carStation.setC_total(total);
        carStation.setC_price(price);
        carStation.setC_pricetime(time);
        Integer count=carStationService.addCarStation(carStation);

        return count;
    }

    /**
     * 删除车库
     * */
    @RequestMapping("/delStation")
    @ResponseBody
    public Map<String,Object> delStation(Integer c_id){
        Map<String,Object> map=carStationService.delStation(c_id);

        return map;
    }
    /**
    * 修改车库信息
    * */
    @RequestMapping("/updateCarstation")
    @ResponseBody
    public Integer updateCarstation(Integer c_id,String c_name,String c_location,String c_description,double c_price,double c_time,Integer total){
        System.out.println(total);
        return carStationService.updateCarstation(c_id,c_name,c_location,c_description,c_price,c_time,total);
    }
}
