package com.example.service;

import com.example.dao.entity.DetailedFeesDO;
import com.example.dto.FrontendRegisterDTO;
import com.example.dto.FrontendSettleBillDTO;
import com.example.vo.FrontendRegisterVO;
import com.example.vo.FrontendSettleBillVO;

import java.util.List;

public interface FrontendService {


    FrontendRegisterVO register(FrontendRegisterDTO frontendRegisterDTO);

    FrontendSettleBillVO settleBill(FrontendSettleBillDTO frontendSettleBillDTO);

    List<DetailedFeesDO> getDetailFees(String idCard);

    boolean query();

    void exit(String name);
}
