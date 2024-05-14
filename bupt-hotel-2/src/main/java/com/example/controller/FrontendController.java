package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import com.example.dto.FrontendDetailFeesDTO;
import com.example.dto.FrontendFreeRoomDTO;
import com.example.dto.FrontendRegisterDTO;
import com.example.dto.FrontendSettleBillDTO;
import com.example.service.FrontendService;
import com.example.vo.FrontendDetailFeesVO;
import com.example.vo.FrontendRegisterVO;
import com.example.vo.FrontendSettleBillVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/frontend/free-room")
    public Result<Boolean> getFreeRoom(@RequestBody FrontendFreeRoomDTO freeRoomReqDTO) {
        boolean frontendFreeRoomRespDTO = frontendService.getFreeRoom(freeRoomReqDTO);
        return Results.success(frontendFreeRoomRespDTO);
    }

    /**
     * 后台将信息写入数据库
     * @param frontendRegisterDTO 旅客登记信息
     * @return 登记成功，后台返回【房间号 + 房间密码】给前台
     */
    @PostMapping("/frontend/register")
    public Result<FrontendRegisterVO> register(@RequestBody FrontendRegisterDTO frontendRegisterDTO) {
        FrontendRegisterVO frontendRegisterVO = frontendService.register(frontendRegisterDTO);
        return Results.success(frontendRegisterVO);
    }

    /**
     * 根据前台提供的旅客身份信息查询房费和空调费用
     * @param frontendSettleBillDTO 旅客姓名、身份证号、房间号、房间密码
     * @return 费用简洁单
     */
    @GetMapping("/frontend/bill")
    public Result<FrontendSettleBillVO> settleBill(@RequestBody FrontendSettleBillDTO frontendSettleBillDTO) {
        FrontendSettleBillVO frontendSettleBillVO = frontendService.settleBill(frontendSettleBillDTO);
        return Results.success(null);
    }

    /**
     * 查询旅客住宿时间段内的空调详细计费记录
     *
     * @param frontendDetailFeesDTO 房间号、入住时间、离开时间
     * @return 空调费用详细单
     */
    @GetMapping("/frontend/ac-bill")
    public Result<List<FrontendDetailFeesVO>> getDetailFees(@RequestBody FrontendDetailFeesDTO frontendDetailFeesDTO) {
        List<FrontendDetailFeesVO> frontendDetailFeesVO = frontendService.getDetailFees(frontendDetailFeesDTO);
        return Results.success(frontendDetailFeesVO);
    }
}
