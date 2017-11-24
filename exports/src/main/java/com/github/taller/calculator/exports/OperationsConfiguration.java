package com.github.taller.calculator.exports;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationsConfiguration {

    Class<?>[] provides();
}
