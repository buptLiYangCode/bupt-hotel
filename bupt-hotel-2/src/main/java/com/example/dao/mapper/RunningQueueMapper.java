package com.example.dao.mapper;

import com.example.dao.entity.AirConditionerDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RunningQueueMapper {
    void delete(String acNumber);

    void update(AirConditionerDO newAirConditionerDO);


    int count();

    void insert(String acNumber);

    void getAll();
}
