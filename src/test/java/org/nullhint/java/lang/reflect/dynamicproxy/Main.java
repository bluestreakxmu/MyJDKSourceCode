package org.nullhint.java.lang.reflect.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 测试类。
 *
 * @author lixibo
 * @date 2017/9/20
 */
public class Main {
    public static void main(String[] args) {
        InvocationHandler handler = new BuyHandler(new Buyer());
        BuyInterface buyProxy = (BuyInterface) Proxy.newProxyInstance(BuyInterface.class.getClassLoader(),
                new Class<?>[]{BuyInterface.class}, handler);
        buyProxy.buyIPhoneX();
    }
}
