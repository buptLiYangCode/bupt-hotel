package com.example.dao.mapper;

import com.example.dao.entity.AirConditionerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AirConditionerMapper {

    @Select("select * from t_air_conditioner where ac_number = #{acNumber}")
    AirConditionerDO getAirConditioner(String acNumber);


    void update(AirConditionerDO airConditionerDO);
}
