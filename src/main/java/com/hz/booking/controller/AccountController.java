package com.hz.booking.controller;

import com.hz.booking.common.Const;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.pojo.User;
import com.hz.booking.service.AccountServcie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RequestMapping("account")
@RestController
public class AccountController {
    @Resource
    private AccountServcie accountServcie;


    @RequestMapping("/addAccount.do")
    public ServerResponse addAccount(Integer userId, String name, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }

        return accountServcie.addAccount(userId,name);
    }

    @RequestMapping("/getAccount.do")
    public ServerResponse getAccount(Integer accountId,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }

        return accountServcie.getAccount(accountId);
    }

}
