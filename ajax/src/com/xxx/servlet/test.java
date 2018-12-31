package com.xxx.servlet;


import com.sun.xml.internal.ws.developer.Serialization;

import java.lang.annotation.Annotation;
import java.util.HashMap;

public class test implements Serialization {
    @Override
    public String encoding() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
