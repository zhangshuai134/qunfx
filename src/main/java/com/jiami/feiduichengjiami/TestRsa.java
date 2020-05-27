package com.jiami.feiduichengjiami;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: zhangshuai
 * @Date: 2020-05-03 18:47
 * @Description:
 **/
public class TestRsa {

    // 私钥对象
    private PrivateKey sk;
    // 公钥对象
    private PublicKey pk;

    // 私钥字符串
    private String privateKeyStr;
    // 公钥字符串
    private String publicKeyStr;

    // 初始化公钥私钥
    public TestRsa() throws NoSuchAlgorithmException {
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(1024);
        KeyPair keyPair = rsa.generateKeyPair();
        sk = keyPair.getPrivate();
        pk = keyPair.getPublic();

        privateKeyStr = new String(Base64.encodeBase64(sk.getEncoded()));
        publicKeyStr = new String(Base64.encodeBase64(pk.getEncoded()));
    }

    // 公钥加密
    public String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    // 私钥解密
    public String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    // 私钥签名
    public String sign(String str) throws Exception {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(this.sk);
        signature.update(str.getBytes());
        byte[] sign = signature.sign();
        return new String(Base64.encodeBase64(sign));
    }

    // 公钥验证
    public boolean verify(String str, String sign) throws Exception {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(this.pk);
        signature.update(str.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes("UTF-8")));
    }


    public static void main(String[] args) throws Exception {
        String str = "这是加密前的明文";

        TestRsa testRsa = new TestRsa();
        String encrypt = testRsa.encrypt(str, testRsa.publicKeyStr);
        System.out.println("加密后的密文：" + encrypt);

        String decrypt = testRsa.decrypt(encrypt, testRsa.privateKeyStr);
        System.out.println("解密后的明文：" + decrypt);

        // 签名
        String sign = testRsa.sign(str);
        System.out.println("签名结果：" + sign);

        // 验证
        boolean verify = testRsa.verify(str, sign);
        System.out.println("验证结果：" + verify);

        // 验证反例
        String str2 = "这是被恶意修改过的伪原文";
        boolean verify2 = testRsa.verify(str2, sign);
        System.out.println("验证结果2：" + verify2);

    }
}
