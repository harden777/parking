package com.wwl.mapper;

import com.wwl.po.CarSpace;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wwl
 * @Date 2020/12/18 22:56
 * @Version 1.0
 */
@Mapper
@Repository
public interface CarSpaceMapper {

    /**
     * 查询车位状态数量
     * */
    @Select("select count(*) from carspace where s_state=#{state}")
    int countPark(@Param("state") int state);

    /**
     * 查询车位总数
     * */
    @Select("select count(*) from carspace")
    int countSpace();


    /**
     * 根据车库id删除车位
     * */
    @Delete("delete from carspace where c_id=#{c_id}")
    Integer delSpaceAll(Integer c_id);

    /**
     * 查询车库是否挺有车辆
     * */
    @Select("select * from carspace where c_id=#{c_id} and s_state=#{s_state}")
    List<CarSpace> findS_state(Integer c_id, Integer s_state);

    /**
    * 查询车位总数根据id
    * */
    @Select("Select count(*) from carspace where c_id=#{c_id}")
    Integer findCount(Integer c_id);

    /**
     * 查询车位信息
     * */
    @Select("select * from carspace where c_id=#{c_id} limit #{pageSize},#{pageNumer} ")
    @Results(id = "CarSpaceMap",value = {
            @Result(column = "s_id",property = "s_id"),
            @Result(column = "s_name",property = "s_name"),
            @Result(column = "s_state",property = "s_state"),
            @Result(column = "s_type",property = "s_type"),
            @Result(column = "s_code",property = "s_code"),
            @Result(column = "c_id",property = "c_id"),
            @Result(property = "carStation",column = "c_id",one = @One(select = "com.wwl.mapper.CarStationMapper.findByid",fetchType = FetchType.EAGER))

    })
    List<CarSpace> carSpaceList(Integer c_id, Integer pageSize, Integer pageNumer);

    /**
     * 修改车位类型
     * */
    @Update("update carspace set s_type=#{s_type} where s_id=#{s_id}")
    Integer updateS_type(Integer s_id, Integer s_type);

    /**
     * 删除车位和批量删除
     * */
    @Delete("<script>"+
            "delete from carspace where s_id in"+
            "<foreach collection='array' item='s_id' open='(' separator=',' close=')'>#{s_id}</foreach>"+
            "</script>")
    Integer delSpace(Integer[] s_id);


    /**
     * 添加车位
     * */
    @Insert("<script> INSERT INTO carspace "
            + "(s_name,s_state,s_type,s_code,c_id) "
            + "VALUES "
            + "<foreach collection = 'list' item='carspaces' separator=',' > "
            + " (#{carspaces.s_name},#{carspaces.s_state},#{carspaces.s_type},#{carspaces.s_code},#{carspaces.c_id}) "
            + "</foreach>"
            + "</script>")
    Integer addCarspace(List<CarSpace> carSpaces);

    /**
     * 根据id查询车位
     * */
    @Select("select * from carspace where s_id=#{s_id}")
    @Results(id = "CarSpaceId",value = {
            @Result(column = "s_id",property = "s_id"),
            @Result(column = "s_name",property = "s_name"),
            @Result(column = "s_state",property = "s_state"),
            @Result(column = "s_type",property = "s_type"),
            @Result(column = "s_code",property = "s_code"),
            @Result(column = "c_id",property = "c_id"),
            @Result(property = "carStation",column = "c_id",one = @One(select = "com.wwl.mapper.CarStationMapper.findByid",fetchType = FetchType.EAGER))

    })
    CarSpace findCarSpaceById(@Param("s_id") int sid);

    /**
     * 查询车位名称
     * */
    @Select("select * from carspace where s_name=#{s_name}")
    @Results(id = "findSpaceName",value = {
            @Result(column = "s_id",property = "s_id"),
            @Result(column = "s_name",property = "s_name"),
            @Result(column = "s_state",property = "s_state"),
            @Result(column = "s_type",property = "s_type"),
            @Result(column = "s_code",property = "s_code"),
            @Result(column = "c_id",property = "c_id"),
            @Result(property = "carStation",column = "c_id",one = @One(select = "com.wwl.mapper.CarStationMapper.findByid",fetchType = FetchType.EAGER))

    })
    CarSpace findSpaceName(String s_name);


    /**
     * 根据c_id查询
     * */
    @Select("select * from carspace where c_id=#{c_id}")
    List<CarSpace> findSpaceByCid(Integer c_id);

    /**
     * 修改车位名称
     * */
    @Update("update  carspace set s_name=#{s_name} where s_id=#{s_id}")
    void updateS_name(CarSpace carSpace);

    /**
     * 根据sid查询
     * */
    @Select("select * from carspace where s_id=#{s_id} ")
    @Results(id = "findSpaceBySid",value = {
            @Result(column = "s_id",property = "s_id"),
            @Result(column = "s_name",property = "s_name"),
            @Result(column = "s_state",property = "s_state"),
            @Result(column = "s_type",property = "s_type"),
            @Result(column = "s_code",property = "s_code"),
            @Result(column = "c_id",property = "c_id"),
            @Result(property = "carStation",column = "c_id",one = @One(select = "com.wwl.mapper.CarStationMapper.findByid",fetchType = FetchType.EAGER))
    })
    CarSpace findSpaceBySid(int sid);
    /**
     * 修改车辆状态
     * */
    @Update("update carspace set s_state=#{s_state} where s_id=#{s_id}")
    void updateS_state(CarSpace carSpace);


}
