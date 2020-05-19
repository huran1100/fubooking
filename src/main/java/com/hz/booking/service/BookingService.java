package com.hz.booking.service;

import com.hz.booking.common.ServerResponse;
import com.hz.booking.vo.Page;

import java.math.BigDecimal;
import java.text.ParseException;

public interface BookingService {
    ServerResponse booking(String spendTime, Integer userId, Integer spendUserId,Integer accountId,
                           Integer type, Integer cateogryId, BigDecimal money,String picture, String remark) throws ParseException;

    ServerResponse getCategory(Integer userId);

    ServerResponse addCategory(Integer userId,String name);

    ServerResponse delCategory(Integer categoryId);

    ServerResponse getCompanion(Integer accountId);

    ServerResponse getBill(Integer accountId, String spendTime, Page page);

    ServerResponse getBillInfo(Integer billId);
}
