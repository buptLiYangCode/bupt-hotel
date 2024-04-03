package com.example.service;

import com.example.dto.req.DetailFeesReqDTO;
import com.example.dto.req.FreeRoomReqDTO;
import com.example.dto.req.RegisterReqDTO;
import com.example.dto.req.SettleBillReqDTO;
import com.example.dto.resp.DetailFeesRespDTO;
import com.example.dto.resp.FreeRoomRespDTO;
import com.example.dto.resp.RegisterRespDTO;
import com.example.dto.resp.SettleBillRespDTO;


public interface UserService {
    FreeRoomRespDTO getFreeRoom(FreeRoomReqDTO freeRoomReqDTO);

    RegisterRespDTO register(RegisterReqDTO registerReqDTO);

    SettleBillRespDTO settleBill(SettleBillReqDTO settleBillReqDTO);

    DetailFeesRespDTO getDetailFees(DetailFeesReqDTO detailFeesReqDTO);

}
