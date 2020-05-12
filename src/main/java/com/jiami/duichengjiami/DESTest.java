package com.jiami.duichengjiami;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Author: zhangshuai
 * @Date: 2020-04-22 22:38
 * @Description:
 **/
public class DESTest {
    public static void main(String[] args) throws Exception {
        String name = "hello word";
        String password = getPassword();
//        String password = "1122334455667788";
        System.out.println("秘钥："+password);

        byte[] encrypt = encrypt(name.getBytes(), password);
        String encryptString = Hex.encodeHexString(encrypt);
        System.out.println("秘钥加密后的密文："+encryptString);

        byte[] decodeHex = Hex.decodeHex(encryptString.toCharArray());
        byte[] decrypt = decrypt(decodeHex, password);
        System.out.println("秘钥解密后的明文："+new String(decrypt));
    }

    /**
     * 获取随机秘钥
     */
    public static String getPassword() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyEncoded = secretKey.getEncoded();
        return Hex.encodeHexString(keyEncoded);
    }

    /**
     * 加密
     */
    public static  byte[] encrypt(byte[] datasource, String password) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 解密
        return cipher.doFinal(src);
    }
}
