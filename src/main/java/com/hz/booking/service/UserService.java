package com.hz.booking.service;

import com.hz.booking.common.ServerResponse;

public interface UserService {

    ServerResponse userLogin(String username, String password);

    ServerResponse register(String username, String password,String email,String nickname);

}
