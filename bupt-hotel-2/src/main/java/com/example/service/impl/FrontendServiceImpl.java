package com.example.service.impl;

import com.example.dto.req.FrontendDetailFeesReqDTO;
import com.example.dto.req.FrontendFreeRoomReqDTO;
import com.example.dto.req.FrontendRegisterReqDTO;
import com.example.dto.req.FrontendSettleBillReqDTO;
import com.example.dto.resp.FrontendDetailFeesRespDTO;
import com.example.dto.resp.FrontendFreeRoomRespDTO;
import com.example.dto.resp.FrontendRegisterRespDTO;
import com.example.dto.resp.FrontendSettleBillRespDTO;
import com.example.service.FrontendService;
import org.springframework.stereotype.Service;

@Service
public class FrontendServiceImpl implements FrontendService {

    /**
     * 查询某个【时间段】是否有某种类型【0：标准间 1：大床房】空闲房间
     * @param freeRoomReqDTO 房间类型、入住时间、离开时间
     * @return t / f 返回结果给前台
     */
    @Override
    public FrontendFreeRoomRespDTO getFreeRoom(FrontendFreeRoomReqDTO freeRoomReqDTO) {
        return null;
    }

    /**
     * 后台将信息写入数据库
     * @param frontendRegisterReqDTO 用户登记信息
     * @return 登记成功，后台返回【房间号 + 房间密码】给前台
     */
    @Override
    public FrontendRegisterRespDTO register(FrontendRegisterReqDTO frontendRegisterReqDTO) {
        return null;
    }
    /**
     * 根据用户身份信息查询房费和空调费用
     * @param frontendSettleBillReqDTO 旅客姓名、身份证号、房间号、房间密码
     * @return 费用简洁单
     */
    @Override
    public FrontendSettleBillRespDTO settleBill(FrontendSettleBillReqDTO frontendSettleBillReqDTO) {
        return null;
    }

    /**
     * 查询用户住宿时间段内的空调详细计费记录
     *
     * @param frontendDetailFeesReqDTO 房间号、入住时间、离开时间
     * @return 空调费用详细单
     */
    @Override
    public FrontendDetailFeesRespDTO getDetailFees(FrontendDetailFeesReqDTO frontendDetailFeesReqDTO) {
        return null;
    }
}
