package com.prototype;

import lombok.Data;

import java.io.*;

/**
 * @Author: zhangshuai
 * @Date: 2020-04-01 00:46
 * @Description:
 **/

public class A implements Serializable,Cloneable{
    private String code;
    private String name;
    private B b;

    public A(String code, String name, B b) {
        this.code = code;
        this.name = name;
        this.b = b;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //序列化，以对象的方式输出去
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            //反序列化，再以对象的方式写回来，所有的引用类型自然都会带上了
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);

            A copyResult = (A)ois.readObject();

            return copyResult;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        A a = new A("00","name1",new B());
        A a1 = (A)a.clone();
        a1.setCode("01");
        A a2 = (A)a.clone();
        a2.setCode("02");
        System.out.println(a.b.hashCode());
        System.out.println(a1.b.hashCode());
        System.out.println(a2.b.hashCode());

    }

}
