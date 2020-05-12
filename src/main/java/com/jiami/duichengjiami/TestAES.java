package com.jiami.duichengjiami;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class TestAES {
    public static void main(String[] args) throws Exception {
        String name = "hello word";
        byte[] password = getPassword();
        System.out.println("秘钥：" + Hex.encodeHexString(password));

        byte[] encrypt = encrypt(name.getBytes(), password);
        String encryptString = Hex.encodeHexString(encrypt);
        System.out.println("秘钥加密后的密文：" + encryptString);

        byte[] decodeHex = Hex.decodeHex(encryptString.toCharArray());
        byte[] decrypt = decrypt(decodeHex, password);
        System.out.println("秘钥解密后的明文：" + new String(decrypt));
    }

    /**
     * 获取随机秘钥
     */
    public static byte[] getPassword() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyEncoded = secretKey.getEncoded();
        return keyEncoded;
    }

    /**
     * 获取专用密钥
     */
    private static Key getSecretKey(byte[] key) {
        try {
            return new SecretKeySpec(key, "AES");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] datasource, byte[] password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
            // 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(datasource);// 加密
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] src, byte[] password) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            byte[] result = cipher.doFinal(src);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
