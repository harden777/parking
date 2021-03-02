package com.wwl.mapper;

import com.wwl.po.CarOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wwl
 * @Date 2020/12/18 22:55
 * @Version 1.0
 */
@Mapper
@Repository
public interface CarOrderMapper {

     /**
      * 今日工作
      * */
     @Select("select * from carorder where endTime Between #{startTime} and #{endTime} and state=1")
     List<CarOrder> findByTime(String startTime, String endTime);

    /**
     * 车位查询
     * */
    @Select("select * from carorder where province like #{province} and carNumber like #{carNumber} and state=0")
    CarOrder searchCarNumber(@Param("province") String province, @Param("carNumber") String carNumber);

    /**
     * 查询订单车牌
     * */
    @Select("select * from carorder where province=#{province} and carNumber=#{carNumber} and state=0")
    CarOrder findCarorder(String province, String carNumber);

    /**
     * 车辆出库
     * */
    @Update("update carorder set endTime=#{endTime},cost=#{cost},time=#{time},state=#{state} where id=#{id}")
    Integer OutCar(CarOrder carOrder);

    /**
     * 查询订单总数
     * */
    @Select("select count(*) from carorder")
    Integer findCount();
    /**
     * 订单分页
     * */
    @Select("select * from carorder limit #{pageSize},#{pageNumer} ")
    @Results(id = "recordList",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "province",property = "province"),
            @Result(column = "carNumber",property = "carNumber"),
            @Result(column = "customerName",property = "customerName"),
            @Result(column = "customerPhone",property = "customerPhone"),
            @Result(column = "startTime",property = "startTime"),
            @Result(column = "endTime",property = "endTime"),
            @Result(column = "time",property = "time"),
            @Result(column = "cost",property = "cost"),
            @Result(column = "state",property = "state"),
            @Result(column = "cid",property = "cid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "is",property = "is"),
            @Result(property = "carSpace",column = "sid",one = @One(select = "com.wwl.mapper.CarSpaceMapper.findCarSpaceById",fetchType = FetchType.EAGER)),
            @Result(property = "user",column = "uid",one = @One(select = "com.wwl.mapper.UserMapper.findByid",fetchType = FetchType.EAGER))

    })
    List<CarOrder> recordList(Integer pageSize, Integer pageNumer);
    /**
     * 删除订单
     * */
    @Delete("delete from carorder where id=#{id}")
    Integer delOrder(Integer id);
    /**
     * 入库车辆查询
     * */
    @Select("select * from carorder where sid=#{sid} and state=0")
    CarOrder orderInfo(Integer sid);

    /**
     * 车辆入库
     * */
    @Insert("insert into carorder values(null,#{province},#{carNumber},#{customerName},#{customerPhone},#{startTime},null,#{cost},#{time},#{state},#{cid},#{sid},#{uid},#{is}) ")
    Integer parkingInto(CarOrder carOrder);
}
