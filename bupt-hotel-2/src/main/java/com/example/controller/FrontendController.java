package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import com.example.dto.req.FrontendDetailFeesReqDTO;
import com.example.dto.req.FrontendFreeRoomReqDTO;
import com.example.dto.req.FrontendRegisterReqDTO;
import com.example.dto.req.FrontendSettleBillReqDTO;
import com.example.dto.resp.FrontendDetailFeesRespDTO;
import com.example.dto.resp.FrontendFreeRoomRespDTO;
import com.example.dto.resp.FrontendRegisterRespDTO;
import com.example.dto.resp.FrontendSettleBillRespDTO;
import com.example.service.FrontendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@RestController
@RequiredArgsConstructor
public class FrontendController {

    private final FrontendService frontendService;

    /**
     * 查询某个【时间段】是否有某种类型【0：标准间 1：大床房】空闲房间
     * @param freeRoomReqDTO 房间类型、入住时间、离开时间
     * @return t / f 返回结果给前台
     */
    @GetMapping("/frontend/freeRoom")
    public Result<FrontendFreeRoomRespDTO> getFreeRoom(@RequestBody FrontendFreeRoomReqDTO freeRoomReqDTO) {
        FrontendFreeRoomRespDTO frontendFreeRoomRespDTO = frontendService.getFreeRoom(freeRoomReqDTO);
//        return frontendFreeRoomRespDTO;
        return Results.success(frontendFreeRoomRespDTO);
    }

    /**
     * 后台将信息写入数据库
     * @param frontendRegisterReqDTO 旅客登记信息
     * @return 登记成功，后台返回【房间号 + 房间密码】给前台
     */
    @PostMapping("/frontend/register")
    public Result<FrontendRegisterRespDTO> register(@RequestBody FrontendRegisterReqDTO frontendRegisterReqDTO) {
        FrontendRegisterRespDTO frontendRegisterRespDTO= frontendService.register(frontendRegisterReqDTO);
        return Results.success(null);
    }

    /**
     * 根据前台提供的旅客身份信息查询房费和空调费用
     * @param frontendSettleBillReqDTO 旅客姓名、身份证号、房间号、房间密码
     * @return 费用简洁单
     */
    @GetMapping("/frontend/bill")
    public Result<FrontendSettleBillRespDTO> settleBill(@RequestBody FrontendSettleBillReqDTO frontendSettleBillReqDTO) {
        FrontendSettleBillRespDTO frontendSettleBillRespDTO = frontendService.settleBill(frontendSettleBillReqDTO);
        return Results.success(null);
    }

    /**
     * 查询旅客住宿时间段内的空调详细计费记录
     *
     * @param frontendDetailFeesReqDTO 房间号、入住时间、离开时间
     * @return 空调费用详细单
     */
    @GetMapping("/frontend/ac-bill")
    public Result<FrontendDetailFeesRespDTO> getDetailFees(@RequestBody FrontendDetailFeesReqDTO frontendDetailFeesReqDTO) {
        FrontendDetailFeesRespDTO frontendDetailFeesRespDTO = frontendService.getDetailFees(frontendDetailFeesReqDTO);
        return Results.success(null);
    }
}
