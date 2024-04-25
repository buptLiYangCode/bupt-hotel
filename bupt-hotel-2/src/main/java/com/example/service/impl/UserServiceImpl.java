package com.example.service.impl;

import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.ConfigMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.WaitQueueMapper;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AirConditionerMapper airConditionerMapper;
    private final WaitQueueMapper waitQueueMapper;
    private final ConfigMapper configMapper;
    private final DetailedFeesMapper detailedFeesMapper;


    @Override
    public void openOrClose(String acNumber) {
        airConditionerMapper.getAirConditioner(acNumber);

    }
}
