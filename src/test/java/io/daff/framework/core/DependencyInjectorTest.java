package io.daff.framework.core;

import io.daff.framework.core.bean.DependencyInjector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author daff
 * @since 2021/8/22
 */
public class DependencyInjectorTest {

    @DisplayName("测试依赖注入")
    @Test
    public void testDoIoc() {
        DependencyInjector di = new DependencyInjector();
        di.doIoc();
    }
}
