package com.example.dao.mapper;

import com.example.dao.entity.WaitingQueueDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WaitingQueueMapper {

    @Insert("INSERT INTO t_waiting_queue (ac_number, temperature, wind_speed, request_time) VALUES (#{acNumber}, #{temperature}, #{windSpeed},#{requestTime})")
    void insert(WaitingQueueDO waitingQueueDO);

    @Delete("delete from t_waiting_queue where ac_number = #{acNumber}")
    void delete(String acNumber);

    @Select("select * from t_waiting_queue")
    List<WaitingQueueDO> getAll();

    @Select("select * from t_waiting_queue where ac_number = #{acNumber}")
    WaitingQueueDO select(String acNumber);
}
