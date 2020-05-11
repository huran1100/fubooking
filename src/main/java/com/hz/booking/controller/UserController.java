package com.hz.booking.controller;

import com.hz.booking.common.Const;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public ServerResponse<Map> userLogin(String username, String password, HttpSession session){
        ServerResponse<Map> response = userService.userLogin(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData().get("user"));
            response.getData().put(Const.TOKEN,session.getId());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(String nickname,String username,
                                           String password,
                                           String email) {

        ServerResponse serverResponse = userService.register(nickname,username, password,email);
        return serverResponse;
    }
}
