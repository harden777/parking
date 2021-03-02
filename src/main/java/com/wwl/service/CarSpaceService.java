package com.wwl.service;

import com.wwl.mapper.CarSpaceMapper;
import com.wwl.po.CarSpace;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wwl
 * @Date 2020/12/18 23:00
 * @Version 1.0
 */
@Service
public class CarSpaceService {
    @Autowired
    private CarSpaceMapper carSpaceMapper;
    @Autowired
    private CarStationService carStationService;


    /**
     * 根据车库id删除车位
     * */
    public Integer delSpaceAll(Integer c_id) {
        return carSpaceMapper.delSpaceAll(c_id);
    }

    /**
     * 查询车库是否挺有车辆
     * */
    public List<CarSpace> findS_state(Integer c_id, Integer s_state) {
        return carSpaceMapper.findS_state(c_id,s_state);
    }

    /**
     * 车库分页
     * */
    public Map<String, Object> carSpaceList(Integer c_id, Integer page, Integer limit) {
        Map<String,Object> map=new HashMap<>();

        Integer pageSize=1;
        Integer pageNumer=10;
        if (limit!=null&&!limit.equals("")){
            pageNumer=limit;
        }
        if (page!=null&&!page.equals("")){
            pageSize=page;
        }
       pageSize=(pageSize-1)*pageNumer;
        //查询车位总数
        Integer count=carSpaceMapper.findCount(c_id);
        //查询车位信息
        List<CarSpace> carSpaces=carSpaceMapper.carSpaceList(c_id,pageSize,pageNumer);
        map.put("code",0);
        map.put("data",carSpaces);
        map.put("count",count);
        return map;
    }
    /**
     * 修改车位类型
     * */
    public Integer updateS_type(Integer s_id, Integer s_type) {
        return carSpaceMapper.updateS_type(s_id,s_type);
    }

    /**
     * 删除车位和批量删除
     * */
    public Integer delSpace(Integer[] s_id, Integer c_total,Integer c_id) {
        Integer length=s_id.length;
        //删除车位
       Integer count= carSpaceMapper.delSpace(s_id);

        //修改车库总数
        if (count>=1){
            c_total=c_total-length;
            System.out.println(c_total+"c_total");
            carStationService.updateC_total(c_total,c_id);
        }
       return count;
    }

    /**
     * 添加车位
     * */
    public Integer addCarspace(List<CarSpace> carSpaces) {
        return carSpaceMapper.addCarspace(carSpaces);
    }

    /**
     * 根据id查询车位
     * */
    public CarSpace findCarSpaceById(int sid) {
        return carSpaceMapper.findCarSpaceById(sid);
    }

    /**
     * 查询车位名称
     * */
    public CarSpace findSpaceName(String s_name) {
        return carSpaceMapper.findSpaceName(s_name);
    }



    /**
     * 根据c_id查询
     * */
    public List<CarSpace> findSpaceByCid(Integer c_id) {
        return carSpaceMapper.findSpaceByCid(c_id);
    }
    /**
     * 修改车位名称
     * */
    public void updateS_name(CarSpace carSpace) {
        carSpaceMapper.updateS_name(carSpace);
    }

    /**
     * 根据sid查询
     * */
    public CarSpace findSpaceBySid(int sid) {
        return carSpaceMapper.findSpaceBySid(sid);

    }
    /**
     * 修改车辆状态
     * */
    public void updateS_state(CarSpace carSpace) {
        carSpaceMapper.updateS_state(carSpace);
    }

    /**
     * 根据车库id查询车位
     * */

   /* public List<CarSpace> findSpaceByCid(Integer c_id){
        return carSpaceMapper.findSpaceByCid(c_id);
    }*/
}
