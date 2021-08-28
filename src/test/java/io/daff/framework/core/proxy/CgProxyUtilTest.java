package io.daff.framework.core.proxy;

import io.daff.framework.utils.CgProxyUtil;

/**
 * @author daff
 * @since 2021/8/28
 */
public class CgProxyUtilTest {

    public static void main(String[] args) {
        SentinelMethodInterceptor sentinelMethodInterceptor = new SentinelMethodInterceptor();
        SleepyPig sleepyPigProxy = (SleepyPig) CgProxyUtil.newProxy(SleepyPig.class, sentinelMethodInterceptor);
        sleepyPigProxy.sleep();
    }
}
