package com.wwl.service;

import com.wwl.mapper.CarStationMapper;
import com.wwl.po.CarSpace;
import com.wwl.po.CarStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author wwl
 * @Date 2020/12/18 23:01
 * @Version 1.0
 */
@Service
public class CarStationService {
    @Autowired
    private CarStationMapper carStationMapper;
    @Autowired
    private CarSpaceService carSpaceService;

    /**
     * 车库信息分页
     * */
    public List<CarStation> carstationList(Integer page, Integer limit) {
        Integer pageSize=1;
        Integer pageNumber=5;
        if (page!=null||!"".equals(page)){
            pageSize=page;
        }
        if (limit!=null||!"".equals(limit)){
            pageNumber=limit;
        }
        pageSize=(pageSize-1)*pageNumber;
        return carStationMapper.carstationList(pageSize,pageNumber);
    }

    /**
     * 车库信息总条数
     * */
    public Integer findCount() {
        return carStationMapper.findCount();
    }

    /**
     * 添加车库
     * */
    public Integer addCarStation(CarStation carStation) {
        carStation.setC_code(UUID.randomUUID().toString().substring(0,5));
        Integer count=carStationMapper.addCarStation(carStation);
        String c_code=carStation.getC_code();
        int c_id=carStationMapper.findByCode(c_code);//根据code 查询出车库id
        //根据车库信息 车位总数添加到车位表
            List<CarSpace> carSpaces=new ArrayList<CarSpace>();
        for (int i = 1; i <= carStation.getC_total(); i++) {
            CarSpace csp = new CarSpace();
            csp.setS_name(carStation.getC_name() + "--" + i);
            csp.setS_state(0);
            csp.setS_type(1);
            csp.setC_id(c_id);
            csp.setS_code(UUID.randomUUID().toString().substring(0,6));
            carSpaces.add(csp);

        }
        carSpaceService.addCarspace(carSpaces);

        return count;
    }
    /**
     * 删除车库
     * */
    public Map<String,Object> delStation(Integer c_id) {
        Map<String,Object> map=new HashMap<>();
        Integer count=0;
         Integer s_state=1;  //车库已停有车辆
        //查询车库是否挺有车辆
        List<CarSpace> carSpace=carSpaceService.findS_state(c_id,s_state);
        if (carSpace.size()==0||carSpace.isEmpty()){ //判断车库是否有车
            count=carStationMapper.delStation(c_id);
            //删除车库同时删除车位
            Integer countSapce=carSpaceService.delSpaceAll(c_id);

            map.put("msg","删除成功");
            map.put("count",count);
            return map;
        }
        map.put("msg","删除失败，请先将该车库车辆出库");
        map.put("count",count);
        return map;
    }
    /**
     * 根据id查询车库
     * */
    public CarStation findByid(Integer c_id) {
        return carStationMapper.findByid(c_id);
    }

    /**
     * 修改车库总数
     * */
    public void updateC_total(Integer c_total, Integer c_id) {
        carStationMapper.updateC_total(c_total,c_id);
    }
    /**
     * 修改车库信息
     * */
    public Integer updateCarstation(Integer c_id, String c_name, String c_location, String c_description, double c_price, double c_time,Integer total) {


       List<CarSpace> carSpaces=carSpaceService.findSpaceByCid(c_id);
        System.out.println(carSpaces);
        if (carSpaces.size()>0&&!carSpaces.isEmpty()){
            for (CarSpace carSpace:carSpaces){
                carSpace.setS_name(c_name+"--"+(carSpaces.indexOf(carSpace)+1));
                carSpaceService.updateS_name(carSpace);

            }

        }

        return carStationMapper.updateCarstation(c_id,c_name,c_location,c_description,c_price,c_time);
    }

    /**
     * 车库查询
     * */
    public List<CarStation> findCarStationList() {
        return carStationMapper.findCarStationList();
    }
    /**
     * 查询车位
     * */
    public CarStation findCarStations(Integer c_id) {
        return carStationMapper.findCarStations(c_id);
    }
}
