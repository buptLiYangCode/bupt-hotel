package com.example.service;

import com.example.dto.req.FrontendDetailFeesReqDTO;
import com.example.dto.req.FrontendFreeRoomReqDTO;
import com.example.dto.req.FrontendRegisterReqDTO;
import com.example.dto.req.FrontendSettleBillReqDTO;
import com.example.dto.resp.FrontendDetailFeesRespDTO;
import com.example.dto.resp.FrontendFreeRoomRespDTO;
import com.example.dto.resp.FrontendRegisterRespDTO;
import com.example.dto.resp.FrontendSettleBillRespDTO;

import java.util.List;

public interface FrontendService {

    FrontendFreeRoomRespDTO getFreeRoom(FrontendFreeRoomReqDTO freeRoomReqDTO);

    FrontendRegisterRespDTO register(FrontendRegisterReqDTO frontendRegisterReqDTO);

    FrontendSettleBillRespDTO settleBill(FrontendSettleBillReqDTO frontendSettleBillReqDTO);

    List<FrontendDetailFeesRespDTO> getDetailFees(FrontendDetailFeesReqDTO frontendDetailFeesReqDTO);
}
