package com.hw.DevHub.global.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathConst {

    public static String PATH_ROOT;

    @Value("${root.path}")
    public void setPathRoot(String pathRoot) {
        PATH_ROOT = pathRoot;
    }
}
