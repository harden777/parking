package com.wwl.mapper;

import com.wwl.po.CarStation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wwl
 * @Date 2020/12/18 22:57
 * @Version 1.0
 */
@Mapper
@Repository
public interface CarStationMapper {
    /**
     * 车库总数
     * */
    @Select("select count(*) from carstation")
    int count();
    /**
     * 车库信息分页
     * */
    @Select("select * from carstation limit #{pageSize},#{pageNumber}")
    List<CarStation> carstationList(Integer pageSize, Integer pageNumber);
    /**
     * 车库信息总条数
     * */
    @Select("select count(*) from carstation")
    Integer findCount();
    /**
     * 添加车库
     * */
    @Insert("insert into carstation values(null,#{c_name},#{c_location},#{c_description},#{c_total},#{c_code},#{c_price},#{c_pricetime})")
    Integer addCarStation(CarStation carStation);
    /**
     * 根据code查询车库id
    * */
    @Select("select c_id from carstation where c_code=#{c_code}")
    int findByCode(String c_code);
    /**
     * 删除车库
     * */
    @Delete("delete from carstation where c_id=#{c_id}")
    Integer delStation(Integer c_id);
    /**
     * 根据id查询车库
     * */
    @Select("select * from carstation where c_id=#{c_id}")
    CarStation findByid(Integer c_id);

    /**
     * 修改车库总数
     * */
    @Update("update carstation set c_total=#{c_total} where c_id=#{c_id}")
    void updateC_total(Integer c_total, Integer c_id);

    /**
     * 修改车库信息
     * */
    @Update("update carstation set c_name=#{c_name},c_location=#{c_location} ,c_description=#{c_description} ,c_price=#{c_price},c_pricetime=#{c_pricetime} where c_id=#{c_id}")
    Integer updateCarstation(Integer c_id, String c_name, String c_location, String c_description, double c_price, @Param("c_pricetime") double c_time);
    /**
     * 车库查询
     * */
    @Select("select * from carstation")
    List<CarStation> findCarStationList();

    /**
     * 查询车位
     * */
    @Select("Select * from carstation where c_id=#{c_id}" )
    @Results(id = "findCarStations",value = {
            @Result(column = "c_id",property = "c_id"),
            @Result(column = "c_code",property = "c_code"),
            @Result(column = "c_name",property = "c_name"),
            @Result(column = "c_total",property = "c_total"),
            @Result(column = "c_location",property = "c_location"),
            @Result(column = "c_description",property = "c_description"),
            @Result(column = "c_price",property = "c_price"),
            @Result(column = "c_pricetime",property = "c_pricetime"),
            @Result(property = "spaceList",column = "c_id",many = @Many(select = "com.wwl.mapper.CarSpaceMapper.findSpaceByCid",fetchType = FetchType.EAGER))

    })
    CarStation findCarStations(Integer c_id);
}
