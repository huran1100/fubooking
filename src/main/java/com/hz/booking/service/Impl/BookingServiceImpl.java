package com.hz.booking.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.dao.BillMapper;
import com.hz.booking.dao.CategoryMapper;
import com.hz.booking.dao.UserMapper;
import com.hz.booking.pojo.Bill;
import com.hz.booking.pojo.Category;
import com.hz.booking.service.BookingService;
import com.hz.booking.util.DateTimeUtil;
import com.hz.booking.vo.BillListVo;
import com.hz.booking.vo.BillVo;
import com.hz.booking.vo.Page;
import com.hz.booking.vo.UserVo;
import org.apache.ibatis.javassist.runtime.Desc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {
    @Resource
    private BillMapper billMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private UserMapper userMapper;

    public ServerResponse booking(String spendTime, Integer userId, Integer spendUserId,Integer accountId,
                                  Integer type, Integer cateogryId, BigDecimal money,
                                  String picture,String remark) throws ParseException {
        if(accountId == null||accountId == 0){
            return ServerResponse.createByErrorMessage("请添加一个账本");
        }
        Bill bill = new Bill();
        bill.setType(type);
        bill.setCategoryId(cateogryId);
        bill.setMoney(money);
        bill.setRemark(remark);
        bill.setUserId(userId);
        bill.setSpendUserId(spendUserId);
        bill.setPicture(picture);
        bill.setAccountId(accountId);
        Date spTime = DateTimeUtil.strToDate(spendTime,"yyyy-MM-dd");
        bill.setSpendTime(spTime);
        int count = billMapper.insertSelective(bill);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }

        return  ServerResponse.createByError();
    }

    public ServerResponse getCategory(Integer userId){
        List<Category> categoryList= categoryMapper.getCategory(userId);
        if(categoryList.size()>0){
            return ServerResponse.createBySuccess("查询成功",categoryList);
        }
        return ServerResponse.createByErrorMessage("");

    }

    public ServerResponse getCompanion(Integer accountId){
        List<UserVo> userList = userMapper.getCompanion(accountId);
        if (userList.size() >0 ) {
            return ServerResponse.createBySuccess("查询成功",userList);
        }
        return ServerResponse.createByErrorMessage("获取合作用户失败");
    }

    public ServerResponse getBill(Integer accountId, String spendTime,Page page ){
        PageHelper.startPage(page.getCurrPageNo(),page.getPageSize(),"spend_time desc" );
        //根据月份查询日期
        List<BillListVo> billList= billMapper.getBillDay(spendTime,accountId);

        //List<BillListVo> billList =new ArrayList<BillListVo>();
        //根据日期查询记账
        for (int i = 0; i < billList.size(); i++) {
            Date everyDay =  billList.get(i).getDay();
            String spendDay = DateTimeUtil.dateToStr(everyDay,"yyyy-MM-dd");
            List<BillVo> bills = billMapper.getBillList(spendDay,accountId);
            /*BillListVo billListVo = new BillListVo();
            billListVo.setDay(everyDay);
            billListVo.setBillList(bills);*/
            billList.get(i).setBillList(bills);
        }
        if(billList.size()>0){
            PageInfo pageInfo= new PageInfo(billList);
            return ServerResponse.createBySuccess("查询成功",pageInfo);
        }
        return  ServerResponse.createByErrorMessage("未找到数据");



    }

    public ServerResponse getBillInfo(Integer billId){
        if(billId ==null){
            return ServerResponse.createByErrorMessage("缺少id");
        }
        BillVo billInfo = billMapper.getBillInfo(billId);
        if(billInfo !=null){
            return  ServerResponse.createBySuccess(billInfo);
        }
        return ServerResponse.createByErrorMessage("未找到数据");
    }




}
