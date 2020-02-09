package com.hz.booking.service.Impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.dao.AccountMapper;
import com.hz.booking.dao.UserMapper;
import com.hz.booking.pojo.Account;
import com.hz.booking.pojo.User;
import com.hz.booking.service.AccountServcie;
import com.hz.booking.util.ShareCodeUtil;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountService")
public class AccountServiceImpl implements AccountServcie {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private UserMapper userMapper;

    public ServerResponse addAccount(Integer userId,String name) {
        //添加账本
        Account account = new Account();
        account.setName(name);
        account.setUserId(userId);
        int count = accountMapper.insertSelective(account);
        //根据id添加邀请码
        if(count >0){
            Integer accountId = account.getId();
            //生成邀请码
            String shareCode = ShareCodeUtil.toSerialCode(accountId);
            Account accountCode = new Account();
            accountCode.setId(accountId);
            accountCode.setInvitation(shareCode);
            int suc = accountMapper.updateByPrimaryKeySelective(accountCode);
            //切换为当前账本
            User user = new User();
            user.setId(userId);
            user.setCurrentAccountId(accountId);
            userMapper.updateByPrimaryKeySelective(user);
            if (suc > 0) {
                return ServerResponse.createBySuccessMessage("添加成功");
            }
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    public ServerResponse getAccount(Integer accountId) {
        Account account =accountMapper.selectByPrimaryKey(accountId);
        if(account!=null){
            return ServerResponse.createBySuccess(account);
        }
        return ServerResponse.createByErrorMessage("查询失败");

    }


}
