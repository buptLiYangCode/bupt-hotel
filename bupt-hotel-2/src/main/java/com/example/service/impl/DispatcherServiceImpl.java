package com.example.service.impl;

import com.example.dao.entity.AirConditionerDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.RunningQueueMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import com.example.service.DispatcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DispatcherServiceImpl implements DispatcherService {
    private final AirConditionerMapper airConditionerMapper;
    private final RunningQueueMapper runningQueueMapper;
    private final WaitingQueueMapper waitingQueueMapper;


    @Override
    public int queryFreeResources() {
        return runningQueueMapper.count();
    }

    @Override
    public List<String> queryTopKWaitingACNumbers(Integer k) {

        return null;
    }

    @Override
    public void establishConnection(List<String> list) {
        for (String acNumber : list) {
            waitingQueueMapper.delete(acNumber);
           // runningQueueMapper.insert();

            AirConditionerDO airConditionerDO = AirConditionerDO.builder()
                    .acNumber(acNumber)
                    .connecting(true)
                    .connectionTime(System.currentTimeMillis())
                    .build();
            airConditionerMapper.update(airConditionerDO);
        }
    }

    @Override
    public List<String> checkUnworkableACs() {
        runningQueueMapper.getAll();
        return null;
    }

    @Override
    public void releaseResources(List<String> acNumberList) {

    }


}
