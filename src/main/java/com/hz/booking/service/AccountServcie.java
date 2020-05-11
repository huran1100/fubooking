package com.hz.booking.service;

import com.hz.booking.common.ServerResponse;

public interface AccountServcie {
    ServerResponse addAccount(Integer userId, String name);

    ServerResponse getAccount(Integer accountId);

    ServerResponse joinAccount(String code,Integer userId);

    ServerResponse getUserAccount(Integer userId);

    ServerResponse changeAccount(Integer userId,Integer accountId);
}
