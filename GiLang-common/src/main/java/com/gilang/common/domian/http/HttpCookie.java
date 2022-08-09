package com.gilang.common.domian.http;

import lombok.Data;

/**
 * @author gylang
 * data 2022/8/8
 */
@Data
public class HttpCookie {


    private final String name;
    private String value;
    private boolean wrap;
    private String domain;
    private String path;
    private long maxAge = Long.MIN_VALUE;
    private boolean secure;
    private boolean httpOnly;
    private SameSite sameSite;

    public HttpCookie(String name) {
        this.name = name;
    }

    public enum SameSite {
        Lax,
        Strict,
        None;

        /**
         * Return the enum value corresponding to the passed in same-site-flag, using a case insensitive comparison.
         *
         * @param name value for the SameSite Attribute
         * @return enum value for the provided name or null
         */
        static SameSite of(String name) {
            if (name != null) {
                for (SameSite each : SameSite.class.getEnumConstants()) {
                    if (each.name().equalsIgnoreCase(name)) {
                        return each;
                    }
                }
            }
            return null;
        }
    }
}
