package com.hz.booking.controller;

import com.hz.booking.common.Const;
import com.hz.booking.common.ResponseCode;
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
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return accountServcie.addAccount(userId,name);
    }

    @RequestMapping("/getAccount.do")
    public ServerResponse getAccount(Integer accountId,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return accountServcie.getAccount(accountId);
    }

    @RequestMapping("/joinAccount.do")
    public ServerResponse joinAccount(String code,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return accountServcie.joinAccount(code,user.getId());
    }

    @RequestMapping("/getUserAccount.do")
    public ServerResponse getUserAccount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return  accountServcie.getUserAccount(user.getId());
    }
    @RequestMapping("/changeAccount.do")
    public ServerResponse changeAccount(HttpSession session,Integer accountId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return accountServcie.changeAccount(user.getId(),accountId);
    }



}
