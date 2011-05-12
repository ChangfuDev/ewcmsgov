package com.ewcms.security.acls.domain;

import org.springframework.security.core.AuthenticationException;

public class PermissionNotFoundException extends AuthenticationException {

    /**
     * Constructs a <code>PermissionNotFoundException</code> with the specified
     * message.
     *
     * @param msg the detail message.
     */
    public PermissionNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>PermissionNotFoundException</code>, making use of the <tt>extraInformation</tt>
     * property of the superclass.
     *
     * @param msg the detail message
     * @param extraInformation additional information such as the username.
     */
    public PermissionNotFoundException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }

    /**
     * Constructs a <code>PermissionNotFoundException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message.
     * @param t root cause
     */
    public PermissionNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

}
