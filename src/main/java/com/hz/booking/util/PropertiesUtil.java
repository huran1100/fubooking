package com.hz.booking.util;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;
    private static Properties props1;

    static {
        String fileName = "booking.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    static {
        String fileName = "application.properties";
        props1 = new Properties();
        try {
            props1.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }


    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty1(String key){
        String value = props1.getProperty(key.trim());
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isEmpty(value)){
            value = defaultValue;
        }
        return value.trim();
    }



}
