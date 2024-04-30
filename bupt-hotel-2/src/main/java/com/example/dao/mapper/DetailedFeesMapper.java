package com.example.dao.mapper;

import com.example.dao.entity.DetailedFeesDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailedFeesMapper {

    @Insert("INSERT INTO t_detailed_fees (ac_number, wind_speed, temperature, start_time, minutes, fee) " +
            "VALUES (#{acNumber}, #{windSpeed}, #{temperature}, #{startTime}, #{minutes}, #{fee})")
    void insert(DetailedFeesDO detailedFeesDO);
}
