package com.duykypaul.dcomapi.common;

import java.util.Arrays;
import java.util.List;

public class Constant {
    public static final String DCOM_API_URL = "http://localhost:1102/api";

    public static class Auth {
        public static final String ADMIN_EMAIL = "lminh9812@gmail.com";
        public static final String ADMIN_PASSWORD = "890*()iop";
        public static final String ADMIN_NAME = "admin";
        public static final int EXPIRATION = 60 * 24;
    }

    public static class Category {
        public static final List<String> LST_CATEGORY = Arrays.asList("Ảnh bựa", "Ảnh troll", "Ảnh hài");
    }
}
