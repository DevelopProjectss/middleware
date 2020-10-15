package org.wyyt.db2es.admin.common;

/**
 * The constant information
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
public class Constants {
    public static final String DEFAULT_ADMIN_USER_NAME = "admin";
    public static final String CURRENT_ADMIN_LOGIN = "current_admin_login";
    public static final String DEFAULT_ADMIN_PASSWORD = Utils.hash("admin");
    public static final Integer MAX_PAGE_NUM = 1000;
    public static final String SYSTEM_ROLE_NAME = "超级管理员";
}