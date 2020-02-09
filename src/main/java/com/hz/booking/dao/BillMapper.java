package com.hz.booking.dao;

import com.hz.booking.pojo.Bill;
import com.hz.booking.vo.BillVo;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;

public interface BillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bill record);

    int insertSelective(Bill record);

    Bill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    List<Bill> getBill(@Param("accountId")Integer accountId, @Param("spendTime")Data spendTime);

    List<Bill> getBillDay(@Param("spendTime") String spendTime, @Param("accountId")Integer accountId);

    List<BillVo> getBillList(@Param("spendTime") String spendTime, @Param("accountId")Integer accountId);
}