package com.wwl.controller;

import com.wwl.po.CarSpace;
import com.wwl.po.CarStation;
import com.wwl.service.CarSpaceService;
import com.wwl.service.CarStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Author wwl
 * @Date 2020/12/18 22:53
 * @Version 1.0
 */
@Controller
@RequestMapping("/space")
public class CarSpaceController {
    @Autowired
    private CarSpaceService carSpaceService;
    @Autowired
    private CarStationService carStationService;

    /**
     * 车库详细信息
     * */
    @RequestMapping(value = "/details" ,method = RequestMethod.POST)
    public String detailSpace(Integer c_id, Model model){
        //根据id查询车库
        CarStation carStation =carStationService.findByid(c_id);
        model.addAttribute("carStation",carStation);
        return "car/carSpaceDetails";
    }

    /**
     * 车位分页
     * */
    @RequestMapping("/carSpaceList")
    @ResponseBody
    public Map<String,Object> carSpaceList(Integer c_id,Integer page,Integer limit){
        Map<String,Object> map=carSpaceService.carSpaceList(c_id,page,limit);

        return map;
    }
    /**
     * 修改车位类型
     * */
    @RequestMapping("/updateS_type")
    @ResponseBody
    public Integer updateS_type(Integer s_id,Integer s_type){

        return carSpaceService.updateS_type(s_id,s_type);
    }
    /**
     * 删除车位和批量删除
     * */
    @RequestMapping("/delSpace")
    @ResponseBody
    public Integer delSpace(Integer[] s_id,Integer c_total,Integer c_id){
        return carSpaceService.delSpace(s_id,c_total,c_id);
    }

    /**
     * 添加车位
     * */
    @RequestMapping("/addSpaceTotal")
    @ResponseBody
    public Integer addSpaceTotal(Integer c_id,Integer c_total,String c_name,Integer add_total,Integer add_type,Integer total){
        Integer sumTotal=c_total+add_total;

        List<CarSpace> carSpaces=new ArrayList<CarSpace>();
        for (int i=1;i<=add_total;i++){
            CarSpace carSpace=new CarSpace();
            carSpace.setC_id(c_id);
            carSpace.setS_name(c_name+"--"+(i+total));
            carSpace.setS_state(0);
            carSpace.setS_type(add_type);
            carSpace.setC_id(c_id);
            carSpace.setS_code(UUID.randomUUID().toString().substring(0,6));
            carSpaces.add(carSpace);
        }
        //添加车位
        Integer count=carSpaceService.addCarspace(carSpaces);
        if (count>=1){
            //修改车库总数
            carStationService.updateC_total(sumTotal,c_id);
        }
        return count;
    }
    /**
     * 车位信息查询
     * */
    @RequestMapping("/findSpaceBySid")
    @ResponseBody
    public CarSpace findSpaceBySid(Integer s_id){

        return carSpaceService.findSpaceBySid(s_id);
    }
}
