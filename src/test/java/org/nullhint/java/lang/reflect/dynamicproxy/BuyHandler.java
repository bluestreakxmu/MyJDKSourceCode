package org.nullhint.java.lang.reflect.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 统一调用所有方法Handler。
 *
 * @author lixibo
 * @date 2017/9/20
 */
public class BuyHandler implements InvocationHandler {

    private Object target;

    public BuyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=====Begin.....");
        Object result = method.invoke(target, args);
        System.out.println("=====End.......");

        return result;
    }
}
