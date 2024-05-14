package com.example.dao.mapper;

import com.example.dao.entity.AirConditionerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedList;

@Mapper
public interface AirConditionerMapper {

    void update(AirConditionerDO airConditionerDO);

    @Select("select * from t_air_conditioner where ac_number = #{acNumber}")
    AirConditionerDO select(String acNumber);

    @Select("select * from t_air_conditioner")
    LinkedList<AirConditionerDO> getAll();
}
