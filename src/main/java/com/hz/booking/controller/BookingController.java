package com.hz.booking.controller;

import com.hz.booking.common.Const;
import com.hz.booking.common.ResponseCode;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.pojo.User;
import com.hz.booking.service.BookingService;
import com.hz.booking.service.FileService;
import com.hz.booking.util.PropertiesUtil;
import com.hz.booking.vo.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("booking")
@RestController
public class BookingController {

    @Resource
    private BookingService bookingService;
    @Resource
    private FileService fileService;

    @RequestMapping("/booking")
    public ServerResponse booking(String spendTime, Integer userId, Integer spendUserId,Integer accountId,
                                  Integer type, Integer cateogryId, BigDecimal money,
                                  String picture, String remark,HttpSession session) throws ParseException {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        ServerResponse response =bookingService.booking( spendTime,  userId,  spendUserId, accountId,
                                     type,  cateogryId,  money, picture, remark);
        return response;

    }

    @RequestMapping("/getCategory")
    public ServerResponse getCategory(Integer userId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return  bookingService.getCategory(userId);
    }
    @RequestMapping("/getCompanion")
    public ServerResponse getCompanion(Integer accountId,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return bookingService.getCompanion(accountId);

    }
    @RequestMapping("/upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

        Map fileMap = new HashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

    }


    @RequestMapping("getBill.do")
    public ServerResponse getBill(Integer accountId, String spendTime, Page page,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return bookingService.getBill(accountId,spendTime,page);


    }









}
