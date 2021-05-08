package com.jiami.duichengjiami;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;


public class TestPBE {
    public static void main(String[] args) throws Exception {
        String name = "hello word";
        String password = "123456";
        System.out.println("秘钥：" + password);

        PBEParameterSpec salt = getSalt();

        byte[] encrypt = encrypt(name.getBytes(), password, salt);
        String encryptString = Hex.encodeHexString(encrypt);
        System.out.println("秘钥加密后的密文：" + encryptString);

        byte[] decrypt = decrypt(encrypt, password, salt);
        System.out.println("秘钥解密后的明文：" + new String(decrypt));
    }

    /**
     * 获取专用密钥
     */
    private static Key getSecretKey(String password) {
        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            return factory.generateSecret(pbeKeySpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取盐
     */
    private static PBEParameterSpec getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = secureRandom.generateSeed(8);
        // 100次加盐迭代
        return new PBEParameterSpec(salt, 100);

    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] datasource, String password, PBEParameterSpec salt) {
        try {
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            // 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), salt);
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
    public static byte[] decrypt(byte[] src, String password, PBEParameterSpec salt) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), salt);
            byte[] result = cipher.doFinal(src);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
