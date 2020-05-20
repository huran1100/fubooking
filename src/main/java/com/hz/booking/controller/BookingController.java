package com.hz.booking.controller;

import com.hz.booking.common.Const;
import com.hz.booking.common.ResponseCode;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.pojo.User;
import com.hz.booking.service.BookingService;
import com.hz.booking.service.FileService;
import com.hz.booking.util.PropertiesUtil;
import com.hz.booking.vo.Page;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("booking")
@RestController
public class BookingController {

    @Resource
    private BookingService bookingService;
    @Resource
    private FileService fileService;

    @RequestMapping("/booking")
    public ServerResponse booking(String spendTime, Integer spendUserId,Integer accountId,
                                  Integer type, Integer cateogryId, BigDecimal money,
                                  String picture, String remark,HttpSession session) throws ParseException {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        ServerResponse response =bookingService.booking( spendTime,  user.getId(),  spendUserId, accountId,
                                     type,  cateogryId,  money, picture, remark);
        return response;

    }

    @RequestMapping("/getCategory")
    public ServerResponse getCategory(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return  bookingService.getCategory(user.getId());
    }
    @RequestMapping("/addCategory")
    public ServerResponse addCategory(String name,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return  bookingService.addCategory(user.getId(),name);
    }

    @RequestMapping("/delCategory")
    public ServerResponse delCategory(Integer categoryId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return  bookingService.delCategory(categoryId);
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
    @RequestMapping(value = "/image.do", method = RequestMethod.POST)
    public ServerResponse uploadImage(HttpServletRequest request, @RequestParam(value = "file",required = false)MultipartFile file) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String filePath = "/images/" + sdf.format(new Date());
        String imageFolderPath = request.getServletContext().getRealPath(filePath);
        System.out.println(imageFolderPath+"文件路径==========++");
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        StringBuilder imageUrl= new StringBuilder();
        /*imageUrl.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(filePath);*/

        //服务器可访问ip
        imageUrl.append(request.getScheme())
                .append("://")
                .append("47.105.65.207")
                .append(":")
                .append("80")
                .append(request.getContextPath())
                .append(filePath);
        String imageName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(file.getBytes(), new FileOutputStream(new File(imageFolder, imageName)));
            imageUrl.append("/").append(imageName);
            return ServerResponse.createBySuccess("success",imageUrl.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorMessage("上传失败!");
    }

    @RequestMapping("/getBillInfo.do")
    public ServerResponse getBillInfo(Integer billId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return bookingService.getBillInfo(billId);
    }



}
