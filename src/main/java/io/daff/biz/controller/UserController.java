package io.daff.biz.controller;

import io.daff.biz.service.UserService;
import io.daff.framework.core.anno.Autowired;
import io.daff.framework.core.anno.Controller;
import io.daff.framework.core.anno.Qualifier;

/**
 * @author daff
 * @since 2021/8/22
 */
@Controller
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    public void queryUser() {
        userService.queryUser();
    }
}
