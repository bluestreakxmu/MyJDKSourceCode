package org.nullhint.java.lang.reflect.staticproxy;

/**
 * 代理类。
 *
 * @author lixibo
 * @date 2017/9/20
 */
public class BuyProxy implements BuyInterface {
    private Buyer buyer;

    public BuyProxy(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public void buyIPhoneX() {
        System.out.println("=====Begin....");
        buyer.buyIPhoneX();
        System.out.println("=====End......");
    }
}
