package com.example.dao.mapper;

import com.example.dao.entity.DetailedFeesDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DetailedFeesMapper {

    @Insert("INSERT INTO t_detailed_fees (ac_number, wind_speed, start_time, end_time, minutes, fee, fee_rate) " +
            "VALUES (#{acNumber}, #{windSpeed}, #{startTime}, #{endTime}, #{minutes}, #{fee}, #{feeRate})")
    void insert(DetailedFeesDO detailedFeesDO);

    @Select("select * from t_detailed_fees where ac_number = #{acNumber}")
    List<DetailedFeesDO> select(@Param("acNumber") String acNumber);
}
