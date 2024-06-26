package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import com.example.dto.AdminStartDTO;
import com.example.service.AdminService;
import com.example.vo.AdminQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设置空调参数、包括制冷、制热，风速上限
 */
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 空调全部置为可用，同时设置系统参数
     * @param adminStartDTO 空调工作模式、温度调节范围、计费规则
     */
    @PostMapping("/admin/workable")
    public Result<Void> startAirConditioners(@RequestBody AdminStartDTO adminStartDTO) {
        // 将所有空调workable置为0，表示可工作

        return Results.success();
    }

    /**
     * 空调全部置为检修
     */
    @PostMapping("/admin/unworkable")
    public Result<Void> stopAirConditioners() {
        // 将所有空调workable置为1，表示检修中

        return Results.success();
    }

    /**
     * 监控分机运行状态，一屏显示一层10间房空调使用状态
     * @param floor 查询层数
     * @return 空调信息列表
     */
    @GetMapping("/admin/monitor-air-conditioner")
    public Result<List<AdminQueryVO>> monitorRoomUnits(@RequestParam int floor) {
        // 查询对应楼层分机的运行状态信息
        return Results.success(null);
    }

    /**
     * 查询验收需要的5个房间
     * @return 某个时刻5个房间的状态
     */
    @GetMapping("/admin/query")
    public Result<List<AdminQueryVO>> query5() {
        //
        return Results.success(adminService.query5());
    }
}
