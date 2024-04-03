package com.example.service.impl;

import com.example.dto.req.DetailFeesReqDTO;
import com.example.dto.req.FreeRoomReqDTO;
import com.example.dto.req.RegisterReqDTO;
import com.example.dto.req.SettleBillReqDTO;
import com.example.dto.resp.DetailFeesRespDTO;
import com.example.dto.resp.FreeRoomRespDTO;
import com.example.dto.resp.RegisterRespDTO;
import com.example.dto.resp.SettleBillRespDTO;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 这里每个方法都需要去调后台的接口，将参数对象发给后台即可
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户询问前台是否有某种类型【0：标准间 1：大床房】空闲房间
     * @param freeRoomReqDTO 用户信息
     * @return 前台返回从后台得到结果，返回给用户
     */
    @Override
    public FreeRoomRespDTO getFreeRoom(FreeRoomReqDTO freeRoomReqDTO) {

        return null;
    }


    /**
     * 用户提供信息供前台登记，前台通知后台将信息写入数据库
     * @param registerReqDTO 用户登记信息
     * @return 登记成功，前台从后台拿到【房间号 + 房间密码】交给用户
     */
    @Override
    public RegisterRespDTO register(RegisterReqDTO registerReqDTO) {

        return null;
    }




    /**
     * 用户申请结账，将房间号、房间密码发给前台，前台从后台拿到数据，返回费用简单给用户
     * @param settleBillReqDTO 旅客姓名、旅客身份证、房间号、房间密码
     * @return 费用简洁单
     */
    @Override
    public SettleBillRespDTO settleBill(SettleBillReqDTO settleBillReqDTO) {

        return null;
    }

    /**
     * 用户申请获取空调费用详细单
     * @param detailFeesReqDTO 房间号、入住时间、离开时间
     * @return 费用详细单
     */
    @Override
    public DetailFeesRespDTO getDetailFees(DetailFeesReqDTO detailFeesReqDTO) {

        return null;
    }


}
