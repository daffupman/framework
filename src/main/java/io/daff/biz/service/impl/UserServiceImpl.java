package io.daff.biz.service.impl;

import io.daff.biz.service.UserService;
import io.daff.framework.core.anno.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author daff
 * @since 2021/8/22
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void queryUser() {
        List<String> mockUsernames = Arrays.asList("wangzhengjin", "yangyang");
        logger.info("查询出的用户：{}", Arrays.toString(mockUsernames.toArray()));
    }
}
