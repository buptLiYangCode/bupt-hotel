package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.AirConditionerDO;
import com.example.dto.req.UserAirConditionerReqDTO;

public interface UserService extends IService<AirConditionerDO> {
    void updateAirConditionerStatus(UserAirConditionerReqDTO userAirConditionerReqDTO);
}
