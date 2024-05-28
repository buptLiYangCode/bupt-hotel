package com.example.dao.mapper;

import com.example.dao.entity.DetailedFeesDO;
import com.example.dto.FrontendDetailFeesDTO;
import com.example.vo.FrontendDetailFeesVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DetailedFeesMapper {

    @Insert("INSERT INTO t_detailed_fees (ac_number, wind_speed, start_time, end_time, minutes, fee, fee_rate) " +
            "VALUES (#{acNumber}, #{windSpeed}, #{startTime}, #{endTime}, #{minutes}, #{fee}, #{feeRate})")
    void insert(DetailedFeesDO detailedFeesDO);

    @Select("select * from t_detailed_fees where room_number = #{roomNumber} and start_time > #{checkInTime} and start_time < #{checkOutTime}")
    List<FrontendDetailFeesVO> select(FrontendDetailFeesDTO frontendDetailFeesDTO);
}
