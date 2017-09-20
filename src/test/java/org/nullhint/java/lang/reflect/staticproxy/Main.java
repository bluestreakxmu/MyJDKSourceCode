package org.nullhint.java.lang.reflect.staticproxy;

/**
 * 测试类。
 *
 * @author lixibo
 * @date 2017/9/20
 */
public class Main {
    public static void main(String[] args) {
        BuyProxy buyProxy = new BuyProxy(new Buyer());
        buyProxy.buyIPhoneX();
    }
}
