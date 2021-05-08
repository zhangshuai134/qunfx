package com.jiami.duichengjiami;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class Test3DES {

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


    private static Key getSecretKey(byte[] key) {
        try {
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            // 转换为DESede专用密钥
            Key secretKey = factory.generateSecret(deSedeKeySpec);
            return secretKey;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(byte[] datasource, byte[] password) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
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

    public static byte[] decrypt(byte[] src, byte[] password) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            byte[] result = cipher.doFinal(src);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取随机秘钥
     */
    public static byte[] getPassword() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(168);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyEncoded = secretKey.getEncoded();
        return keyEncoded;
    }
}
