package com.hz.booking.dao;

import com.hz.booking.pojo.Account;
import com.hz.booking.vo.UserAccountVo;

import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account getAccountByCode(String invitation);

    List<UserAccountVo> getUserAccount(Integer userId);
}