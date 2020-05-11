package com.hz.booking.dao;

import com.hz.booking.pojo.UserAccount;
import org.apache.ibatis.annotations.Param;

public interface UserAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    UserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);

    int isInvitated(@Param("userId") Integer userId,@Param("accountId") Integer accountId);
}