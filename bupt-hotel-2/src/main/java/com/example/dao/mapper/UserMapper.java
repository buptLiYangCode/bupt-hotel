package com.example.dao.mapper;

import com.example.dao.entity.UserDO;
import com.example.dto.req.FrontendRegisterReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert(" INSERT INTO t_user (name, age, id_card, phone, sex, room_type, room_number, check_in_time, check_out_time)" +
            " VALUES (#{name}, #{age}, #{idCard}, #{phone}, #{sex}, #{roomType}, #{roomNumber}, #{checkInTime}, #{checkOutTime})")
    void insert(FrontendRegisterReqDTO frontendRegisterReqDTO);

    @Select("select * from t_user where id_card = #{idCard}")
    UserDO select(String idCard);
}
