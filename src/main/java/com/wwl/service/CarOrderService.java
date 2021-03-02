package com.wwl.service;

import com.alibaba.fastjson.JSONException;
import com.baidu.aip.ocr.AipOcr;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.wwl.mapper.CarOrderMapper;
import com.wwl.mapper.CarSpaceMapper;
import com.wwl.mapper.CarStationMapper;
import com.wwl.mapper.UserMapper;
import com.wwl.po.CarOrder;
import com.wwl.po.CarSpace;
import com.wwl.po.CarStation;
import com.wwl.po.User;
import com.wwl.utils.Time;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author wwl
 * @Date 2020/12/18 22:59
 * @Version 1.0
 */
@Service
public class CarOrderService {

    private static final String APP_ID = "23472267";
    private static final String API_KEY = "CXqV2CXoP8B52G414kfcMElD";
    private static final String SECRET_KEY = "wPCn3ogWEqiTcRfYnHuAVXDr85PtdD5y";

    @Autowired
    private CarOrderMapper carOrderMapper;
    @Autowired
    private CarSpaceMapper carSpaceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CarStationMapper carStationMapper;
    @Autowired
    private CarSpaceService carSpaceService;
    @Autowired
    private UserService userService;

    /**
     * 今日工作
     * */
    public List<CarOrder> findByTime(String time) {
        String endTime;
        String startTime;
        if(time==null||time.equals("")){
            startTime= Time.getAmTimeByCalendar();

            endTime=Time.getClickTimeByCalendar();

        }else {
            startTime=time+ " " + "00:00:00";

            endTime=time+Time.getClickHour();

        }
        List<CarOrder> carOrders=carOrderMapper.findByTime(startTime,endTime);

        return carOrders;
    }


    /**
     * 查询车库信息车位信息
     * */
    public Map<String, Object> systemState() {
        int pn=carSpaceMapper.countPark(1); //当前停车数
       // int np=carSpaceMapper.countPark(0);//空余车位


        int all=carSpaceMapper.countSpace();//车位总数
        int k=carStationMapper.count();//车库总数
        //空余车位
        int np=all-pn;
        Map<String,Object> s=new HashMap<String, Object>();
        s.put("pn",pn);
        s.put("np",np);
        s.put("all",all);
        s.put("k",k);
        return s;
    }

    /**
     * 车位查询
     * */
    public Map<String, Object> searchCarNumber(String province, String carNumber) {
        Map<String,Object> map=new HashMap<>();
        CarOrder carOrder=carOrderMapper.searchCarNumber(province,carNumber);
        System.out.println(carOrder);
        if (carOrder!=null&&!carOrder.equals("")){
                CarSpace carSpace=carSpaceService.findCarSpaceById(carOrder.getSid());
                map.put("carSpace",carSpace);
            map.put("carOrder",carOrder);
            return map;
        }
        map.put("carOrder",carOrder);
        return map;
    }

    /**
     * 查询order
     * */
    public Map<String, Object> findCarorder(String province, String carNumber) {
        CarOrder carOrder=carOrderMapper.findCarorder(province,carNumber);
        Map<String,Object> map=new HashMap<>();
        if (carOrder!=null&&!carOrder.equals("")){
            //查询车位
            CarSpace carSpace=carSpaceService.findSpaceBySid(carOrder.getSid());
            User user=userService.findByid(carOrder.getUid());
            Timestamp oneEndTime = new Timestamp(new Date().getTime());
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            long endTime = oneEndTime.getTime();
            long startTime = carOrder.getStartTime().getTime();
            double minute = (double) ((endTime - startTime) / (1000 * 60));
            double hour = minute / 60;
            double price = carSpace.getCarStation().getC_price();
            double pricetime = carSpace.getCarStation().getC_pricetime();
            double time = hour / pricetime;
            double cost = time * price;
            map.put("carOrder", carOrder);
            map.put("user", user);
            map.put("carSpace", carSpace);
            map.put("cost", String.format("%.2f", cost));
            map.put("startTime", s.format(startTime));
            map.put("endTime", s.format(oneEndTime));
            map.put("oneEndTime", oneEndTime);
            map.put("time", String.format("%.2f", time));
            return map;
        }
        map.put("carOrder",carOrder);
        return map;
    }
    /**
     * 车辆出库
     * */
    public Integer OutCar(CarOrder carOrder) {
        CarSpace carSpace=new CarSpace();
        carSpace.setS_id(carOrder.getSid());
        carSpace.setS_state(0);
        carSpaceService.updateS_state(carSpace);
        return carOrderMapper.OutCar(carOrder);
    }

    /**
     * 订单分页
     * */
    public Map<String, Object> recordList(Integer page, Integer limit) {
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
        Integer count=carOrderMapper.findCount();
        //查询订单
       List<CarOrder> carOrders=carOrderMapper.recordList(pageSize,pageNumer);
        map.put("code",0);
        map.put("data",carOrders);
        map.put("msg","success");
        map.put("count",count);
        return map;
    }
    /**
     * 删除订单
     * */
    public Integer delOrder(Integer id) {
        return carOrderMapper.delOrder(id);
    }

    /**
     * 入库车辆查询
     * */
    public Map<String, Object> orderInfo(Integer sid) {
        CarOrder carOrder=carOrderMapper.orderInfo(sid);
        Map<String, Object> map=new HashMap<>();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        map.put("startTime",sf.format(carOrder.getStartTime()));
        map.put("carOrder",carOrder);
        return map;
    }
    /**
     * 验证车牌是否存在
     * */
    public CarOrder checkCarNumber(String province, String carNumber) {

        return carOrderMapper.findCarorder(province,carNumber);
    }
    /**
     * 车辆入库
     * */
    public Integer parkingInto(CarOrder carOrder) {
        CarSpace carSpace=new CarSpace();
        carSpace.setS_id(carOrder.getSid());
        carSpace.setS_state(1);
        carSpaceMapper.updateS_state(carSpace);
        return carOrderMapper.parkingInto(carOrder);
    }
    /**
     * 车牌识别调用百度qpi
     * */
    public  String OCRNumberParkIn(byte[] img){
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
       // System.setProperty("aip.log4j.conf", ExportWordUtil.class.getResource("/").getPath() +"/log4j.properties");
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "false");
        // 调用接口
        JSONObject res = client.plateLicense(img, options);
        try {
            System.out.println(res.toString(2));
            if(res.get("words_result") != null) {
                String  number = res.getJSONObject("words_result").getString("number");

                return number;
            }else{
                //失败打印错误信息
                System.out.println(res.getString("error_msg"));
            }
        } catch (JSONException e) {
            //异常信息
            System.out.println(e.getMessage());
        }
        return null;
    }
}
