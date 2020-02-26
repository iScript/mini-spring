package com.macro.fw.web.mvc;

import java.lang.annotation.*;

// 添加元注解
@Documented     // 可以被文档化
@Retention(RetentionPolicy.RUNTIME) // 注解的生命周期
@Target(ElementType.TYPE)   // 注解的使用范围ElementType.TYPE代表类
public @interface Controller {


}
