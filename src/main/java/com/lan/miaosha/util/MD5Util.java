package com.lan.miaosha.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String salt = "1a2b3c4d5f";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * first MD5 , user input password convert to formPassword
     * @param password
     * @return
     */
    public static String inputPassFormPass(String password){
        return md5(password+md5(salt.substring(0,3)));
    }

    /**
     * second MD5 ， formPassword convert to dbPassword
     * @param formPass
     * @param DBSalt
     * @return
     */
    public static String formPassToDBPass(String formPass , String DBSalt){
        return md5(formPass+md5(DBSalt));
    }

    public static String encryptPassword(String password , String DBSalt){
        String formPassWord = inputPassFormPass(password);
        return formPassToDBPass(formPassWord ,DBSalt);
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("123456" , "YGNNBPKN"));
    }

}
