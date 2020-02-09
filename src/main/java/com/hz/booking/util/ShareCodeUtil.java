package com.hz.booking.util;

import java.util.Random;

/**
 * 随机邀请码
 * @author kun.zhang@downjoy.com
 *) 获取id: 1127738 <br/>
 * 2) 使用自定义进制转为：gpm6 <br/>
 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
 *
 */
public class ShareCodeUtil {

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z',
            'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y',
            'l', 't', 'n', '6', 'b', 'g', 'h'};
    private static final char b='o'; /** (不能与自定义进制有重复) */
    private static final int binLen = r.length;/** 进制长度 */
    private static final int s =6;/** 序列最小长度 */
    /**
     * 更加id 生产6为随机码
     * @param id
     * @return
     */
    public static String toSerialCode(long id){
        char[] buf = new char[32];
        int charPos = 32;
        while((id/binLen)>0){
            int intid = (int) (id%binLen);
            buf[--charPos] = r[intid];
            id/=binLen;
        }
        String str = new String(buf,charPos,(32-charPos));
        //不够长度的自动随机补全
        if(str.length()<s){
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random random = new Random();
            for(int i=1;i<s-str.length();i++){
                sb.append(r[random.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str;
    }
    /*public static void main(String[] args) {
        String str =	toSerialCode(2);
        System.out.println(str);
    }*/

}
