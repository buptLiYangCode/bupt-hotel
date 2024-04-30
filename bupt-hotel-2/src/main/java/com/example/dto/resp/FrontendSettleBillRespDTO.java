package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendSettleBillRespDTO {

    /*
    房间单价
     */
    private Double roomPrice;
    /*
    住宿天数
     */
    private Double days;
    /*
    房费
     */
    private Double roomFee;
    /*
    空调费
     */
    private Double airConditionerFee;
    /*
    总费用
     */
    private Double totalFee;
}
