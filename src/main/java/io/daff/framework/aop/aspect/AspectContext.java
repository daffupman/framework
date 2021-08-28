package io.daff.framework.aop.aspect;

/**
 * @author daff
 * @since 2021/8/28
 */
public class AspectContext {

    private final Integer orderIndex;
    private final DefaultAspect aspectObject;

    public AspectContext(Integer orderIndex, DefaultAspect aspectObject) {
        this.orderIndex = orderIndex;
        this.aspectObject = aspectObject;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public DefaultAspect getAspectObject() {
        return aspectObject;
    }
}
