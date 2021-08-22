package io.daff.biz.service.impl;

import io.daff.biz.service.UserService;
import io.daff.framework.core.anno.Service;

import java.util.Arrays;

/**
 * @author daff
 * @since 2021/8/22
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void queryUser() {
        Arrays.asList("wangzhengjin", "yangyang").forEach(System.out::println);
    }
}
