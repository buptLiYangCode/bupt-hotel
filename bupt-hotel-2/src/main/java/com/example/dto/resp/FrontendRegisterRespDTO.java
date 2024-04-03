package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendRegisterRespDTO {
    /** 房间号 */
    private String roomNumber;
    /** 房间密码 */
    private String password;
}
