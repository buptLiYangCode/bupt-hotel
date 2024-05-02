package com.example.dao.mapper;

import com.example.dao.entity.RunningQueueDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RunningQueueMapper {
    @Delete("delete from t_running_queue where ac_number = #{acNumber}")
    void delete(String acNumber);

    @Insert("insert into t_running_queue (ac_number, connection_time) values (#{acNumber}, #{connectionTime})")
    void insert(RunningQueueDO runningQueueDO);

    @Select("select * from t_running_queue")
    List<RunningQueueDO> getAll();

}
