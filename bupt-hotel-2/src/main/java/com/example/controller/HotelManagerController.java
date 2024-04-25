package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 酒店经历和后台系统交互，生成关于空调运营的日报、周报
 */
@RestController
@RequiredArgsConstructor
// TODO：这个类最后再弄
public class HotelManagerController {

    // 生成日报
    @GetMapping("/hotel-manage/daily-report")
    public Result<Void> generateDailyReport(@RequestParam Date date) {

        return Results.success();
    }

    // 生成周报
    @GetMapping("/hotel-manage/weekly-report")
    public Result<Void> generateWeeklyReport(@RequestParam Date startDate, @RequestParam Date endDate) {
        return Results.success();
    }
}
