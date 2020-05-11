package com.hz.booking.service.Impl;

import com.hz.booking.common.ServerResponse;
import com.hz.booking.dao.AccountMapper;
import com.hz.booking.dao.UserAccountMapper;
import com.hz.booking.dao.UserMapper;
import com.hz.booking.pojo.Account;
import com.hz.booking.pojo.User;
import com.hz.booking.pojo.UserAccount;
import com.hz.booking.service.AccountServcie;
import com.hz.booking.util.ShareCodeUtil;
import com.hz.booking.vo.AccountVo;
import com.hz.booking.vo.UserAccountVo;
import com.hz.booking.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountServcie {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAccountMapper userAccountMapper;

    public ServerResponse addAccount(Integer userId,String name) {
        //添加账本
        Account account = new Account();
        account.setName(name);
        account.setUserId(userId);
        account.setCreateTime(new Date());
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
            //添加账本关系
            UserAccount userAccount =new UserAccount();
            userAccount.setAccountId(accountId);
            userAccount.setUserId(userId);
            userAccountMapper.insertSelective(userAccount);

            //切换为当前账本
            User user = new User();
            user.setId(userId);
            user.setCurrentAccountId(accountId);
            userMapper.updateByPrimaryKeySelective(user);
            if (suc > 0) {
                return ServerResponse.createBySuccess("添加成功",accountId);
            }
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    public ServerResponse getAccount(Integer accountId) {
        if(accountId == null){
            return ServerResponse.createByErrorMessage("请添加一个账本");
        }
        Account account =accountMapper.selectByPrimaryKey(accountId);

        if(account!=null){
            User user =userMapper.selectByPrimaryKey(account.getUserId());
            AccountVo accountVo = new AccountVo();
            accountVo.setId(account.getId());
            accountVo.setName(account.getName());
            accountVo.setUserId(account.getUserId());
            accountVo.setUserName(user.getNikename());
            accountVo.setInvitation(account.getInvitation());
            accountVo.setRemark(account.getRemark());
            accountVo.setCreateTime(account.getCreateTime());
            //查询用户
            List<UserVo> userList = userMapper.getCompanion(accountId);
            accountVo.setUserVoList(userList);

            return ServerResponse.createBySuccess(accountVo);
        }
        return ServerResponse.createByErrorMessage("查询失败");

    }

    public ServerResponse joinAccount(String code,Integer userId){

        Account account = accountMapper.getAccountByCode(code);
        if(account ==null){
            return ServerResponse.createByErrorMessage("无效的邀请码");
        }
        Integer accountId = account.getId();

        int count = userAccountMapper.isInvitated(userId,accountId);
        if(count >0){
            return ServerResponse.createByErrorMessage("已加入过改账本");
        }
        User user = new User();
        user.setCurrentAccountId(accountId);
        user.setId(userId);
        userMapper.updateByPrimaryKeySelective(user);

        //添加账本关系
        UserAccount userAccount =new UserAccount();
        userAccount.setAccountId(accountId);
        userAccount.setUserId(userId);
        userAccountMapper.insertSelective(userAccount);
        return  ServerResponse.createBySuccessMessage("加入账本成功");
    }

    public ServerResponse getUserAccount(Integer userId) {
        List<UserAccountVo> accounts = accountMapper.getUserAccount(userId);

        return ServerResponse.createBySuccess(accounts);
    }

    public ServerResponse changeAccount(Integer userId,Integer accountId){
        User user = new User();
        user.setId(userId);
        user.setCurrentAccountId(accountId);
        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count>0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

}
