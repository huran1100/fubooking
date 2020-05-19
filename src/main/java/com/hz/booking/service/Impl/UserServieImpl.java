package com.hz.booking.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.hz.booking.common.ServerResponse;
import com.hz.booking.dao.UserMapper;
import com.hz.booking.pojo.User;
import com.hz.booking.service.UserService;
import com.hz.booking.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServieImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    public ServerResponse userLogin(String username, String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        int count = userMapper.checkUsername(username);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5pass = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5pass);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户密码有误");
        }
        user.setPassword("");
        map.put("user",user);

        return ServerResponse.createBySuccess("登陆成功", map);
    }

    public ServerResponse register(String nickname, String username,String password,String email){
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            return ServerResponse.createByErrorMessage("请输入完整信息");
        }
        //验证用户名称
        int count = userMapper.checkUsername(username);
        if (count > 0) {
            return ServerResponse.createByErrorMessage("该账号已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setNikename(nickname);
        user.setPassword(MD5Util.MD5EncodeUtf8(password));
        int inscount = userMapper.insertSelective(user);
        if (inscount > 0){
            return ServerResponse.createBySuccessMessage("注册成功");
        }

        return ServerResponse.createByErrorMessage("注册失败");
    }
}
