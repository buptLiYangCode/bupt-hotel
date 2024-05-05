package com.example.dao.mapper;

import com.example.dao.entity.DetailedFeesDO;
import com.example.dto.req.FrontendDetailFeesReqDTO;
import com.example.dto.resp.FrontendDetailFeesRespDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DetailedFeesMapper {

    @Insert("INSERT INTO t_detailed_fees (ac_number, wind_speed, temperature, start_time, minutes, fee) " +
            "VALUES (#{acNumber}, #{windSpeed}, #{temperature}, #{startTime}, #{minutes}, #{fee})")
    void insert(DetailedFeesDO detailedFeesDO);

    @Select("select * from t_detailed_fees where room_number = #{roomNumber} and start_time > #{checkInTime} and start_time < #{checkOutTime}")
    List<FrontendDetailFeesRespDTO> select(FrontendDetailFeesReqDTO frontendDetailFeesReqDTO);
}
