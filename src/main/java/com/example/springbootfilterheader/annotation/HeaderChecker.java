package com.example.springbootfilterheader.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HeaderChecker {

    /**
     * Without default value means this argument is required
     *
     * @return Header names
     */
    String[] headerNames();
}

