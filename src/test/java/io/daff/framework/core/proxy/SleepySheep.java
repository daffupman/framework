package io.daff.framework.core.proxy;

import java.util.Random;

/**
 * @author daff
 * @since 2021/8/28
 */
public class SleepySheep implements Sleepy{

    @Override
    public void sleep() throws InterruptedException {
        Random random = new Random();
        int time = random.nextInt(5) * 1000;
        Thread.sleep(time);
    }
}
