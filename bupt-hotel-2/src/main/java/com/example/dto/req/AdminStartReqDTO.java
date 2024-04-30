package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStartReqDTO {
    /*
    空调模式：0表示制冷、1制热
    */
    private Integer mode;

    /*
    调温上限
     */
    private Integer upperBound;

    /*
    调温上限
     */
    private Integer lowerBound;

    /*
    计价规则：每档风速+每度温度：分钟价格
     */
    private HashMap<String, Double> priceMap;
}
