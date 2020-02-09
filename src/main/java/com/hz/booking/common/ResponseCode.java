package com.hz.booking.common;

/**
 * Created by hr
 */
public enum ResponseCode {

    SUCCESS(0,"成功"),
    ERROR(1,"错误"),
    NEED_LOGIN(10,"请登录"),
    ILLEGAL_ARGUMENT(2,"非法参数"),
    FORBIDDEN(3,"禁止");

    private final int code;
    private final String desc;


    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
