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
        Buyer buyer = new Buyer();
        InvocationHandler handler = new BuyHandler(buyer);

        BuyInterface buyProxy = (BuyInterface) Proxy.newProxyInstance(buyer.getClass().getClassLoader(),
                new Class<?>[]{BuyInterface.class}, handler);
        buyProxy.buyIPhoneX();
    }
}
