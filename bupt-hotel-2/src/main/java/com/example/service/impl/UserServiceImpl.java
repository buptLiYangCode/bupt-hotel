package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dto.req.UserAirConditionerReqDTO;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<AirConditionerMapper, AirConditionerDO> implements UserService {

    /**
     * 用户更新空调状态
     *
     * @param userAirConditionerReqDTO 空调状态信息
     */
    @Override
    public void updateAirConditionerStatus(UserAirConditionerReqDTO userAirConditionerReqDTO) {

        //利用mybatis-plus找到对应空调标号的记录
        LambdaQueryWrapper<AirConditionerDO> queryWrapper = Wrappers.lambdaQuery(AirConditionerDO.class)
                .eq(AirConditionerDO::getAirConditionerNumber, userAirConditionerReqDTO.getAirConditionerNumber());
        AirConditionerDO airConditionerDO = baseMapper.selectOne(queryWrapper);
        //如果空调处于检修状态，直接拒绝和用户交互
        if (airConditionerDO.getWorkable() == 1)
            return;

        //1.如果空调状态【用户视角：按下空调关机键】是关闭
        if (airConditionerDO.getStatus() == 1) {
            //如果用户请求打开空调【0】，将空调编号及状态信息加入等待队列表t_wait_queue
            if (userAirConditionerReqDTO.getStatus() == 0) {

            }
            //如果用户请求关闭空调【1】或调整风速、温度【2】，直接返回。必须先按下开机键，打开空调
            return;
        }
        //2.如果空调状态【用户视角：按下空调开机键】是打开
        //2.1如果空调真实状态是打开【出风口正在出风】，就计费
        if (airConditionerDO.getRealStatus() == 0) {
            //2.1.1

            //2.1.2算出距离上次操作分钟数
            Date lastOperationTime = airConditionerDO.getLastOperationTime();
            Instant lastOperationInstant = lastOperationTime.toInstant();
            Instant now = Instant.now();
            long minutesDifference = Duration.between(lastOperationInstant, now).toMinutes();

            //2.1.3根据之前温度和风速，查询价表

            //2.1.4两次操作间产生的费用

            //2.1.5将这段时间产生的费用记录插入空调费用详细表t_detailed_fees

            //2.1.6.更新空调状态、温度、风速、最近操作时间
        }
        //2.2如果空调真实状态是关闭【时间片用完，出风口停止出风】，不计费，将空调编号及状态信息加入等待队列表t_wait_queue
        if (airConditionerDO.getRealStatus() == 1) {

        }

    }
}
