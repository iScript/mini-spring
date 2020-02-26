package com.macro.fw.beans;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)  // 注解到类属性
public @interface AutoWired {
}
