package org.nullhint.java.lang.reflect.dynamicproxy;

import org.nullhint.java.lang.reflect.staticproxy.BuyInterface;

/**
 * 真正的买家。
 *
 * @author lixibo
 * @date 2017/9/20
 */
public class Buyer implements BuyInterface {

    @Override
    public void buyIPhoneX() {
        System.out.println("=====Buyer buys an iPhoneX.");
    }
}