package io.daff.framework.core.proxy;

import io.daff.framework.utils.JdkProxyUtil;

/**
 * @author daff
 * @since 2021/8/28
 */
public class JdkProxyUtilTest {

    public static void main(String[] args) throws InterruptedException {
        SleepySheep sleepySheep = new SleepySheep();
        TimeStatisticInvocationHandler timeStatisticInvocationHandler = new TimeStatisticInvocationHandler(sleepySheep);
        Sleepy sleepySheepProxy = JdkProxyUtil.newProxy(sleepySheep, timeStatisticInvocationHandler);
        sleepySheepProxy.sleep();
    }
}
