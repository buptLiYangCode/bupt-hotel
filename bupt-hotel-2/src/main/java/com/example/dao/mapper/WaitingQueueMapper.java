package com.example.dao.mapper;

import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.WaitQueueDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WaitingQueueMapper {

    @Insert("INSERT INTO t_wait_queue (ac_number, temperature, wind_speed, request_time) VALUES (#{acNumber}, #{temperature}, #{windSpeed},#{requestTime})")
    void insertRequest(WaitQueueDO waitQueueDO);

    void delete(String acNumber);

    void update(AirConditionerDO newAirConditionerDO);

    List<String> getK(Integer k);
}
