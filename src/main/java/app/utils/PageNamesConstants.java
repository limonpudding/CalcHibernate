package app.utils;

public class PageNamesConstants {
    public static final String ROOT_PAGE = "/";
    public static final String CALC_PAGE = "/calc";
    public static final String OPHISTORY_PAGE = "/ophistory";
    public static final String ANSWER_PAGE = "/answer";
    public static final String TABLES_PAGE = "/tables";
    public static final String ABOUT_PAGE = "/about";
    public static final String PAGE_NOT_FOUND_PAGE = "/*";
    public static final String REST_PAGE = "/rest";
    public static final String REST_CALC_PAGE = "/rest/calc";
    public static final String REGISTRATION_PAGE = "/register";
    public static final String REG_PAGE = "/reg";
    public static final String LOGIN_PAGE = "/login";
    public static final String ACCESS_DENIED_PAGE = "/accessDenied";
    public static final String ACCOUNTS_MANAGER_PAGE = "/accountsManager";
    public static final String ROLE_CHANGE_PAGE = "/roleChange";

    public static final String CREATE_DB = "create table USERS\n" +
            "(\n" +
            "  USERNAME             NVARCHAR2(40) not null\n" +
            "    primary key,\n" +
            "  PASSWORD           NVARCHAR2(100) not null\n" +
            ");" +
            "INSERT INTO USERS\n" +
            "(USERNAME,PASSWORD)\n" +
            "VALUES  ('admin', '$2a$10$5a6vv3yJZuAbpUSU04vAce2d6MACeDHJeDspyulKzbR2.tAu5W2Tm');\n" +
            "create table USERROLES\n" +
            "(\n" +
            "  ID int auto_increment primary key, \n" +
            "  USERNAME             NVARCHAR2(40) not null,\n" +
            "  ROLE           NVARCHAR2(40) not null\n" +
            "); " +
            "INSERT INTO USERROLES\n" +
            "(USERNAME,ROLE)\n" +
            "VALUES  ('admin', 'ROLE_ADMIN');\n" +
            "create table BINARYOPERATION\n" +
            "(\n" +
            "  ID             NVARCHAR2(40) not null\n" +
            "    primary key,\n" +
            "  OPERATIONKIND           NVARCHAR2(40),\n" +
            "  FIRSTOPERAND   CLOB,\n" +
            "  SECONDOPERAND CLOB,\n" +
            "  ANSWER         CLOB,\n" +
            "  IDSESSION      NVARCHAR2(40),\n" +
            "  TIME           TIMESTAMP(6)\n" +
            ");" +
            "create table SINGLEOPERATION\n" +
            "(\n" +
            "  ID           NVARCHAR2(40) not null\n" +
            "    primary key,\n" +
            "  OPERATIONKIND         NVARCHAR2(40),\n" +
            "  FIRSTOPERAND CLOB,\n" +
            "  ANSWER       CLOB,\n" +
            "  IDSESSION    NVARCHAR2(40),\n" +
            "  TIME         TIMESTAMP(6)\n" +
            ");" +
            "create table CONSTANTS\n" +
            "(\n" +
            "  KEY            NVARCHAR2(40) default NULL not null\n" +
            "    primary key,\n" +
            "  VALUE  CLOB" +
            ");" +
            "create table SESSIONS\n" +
            "(\n" +
            "  ID        NVARCHAR2(40) default NULL not null\n" +
            "    primary key,\n" +
            "  IP        NVARCHAR2(25),\n" +
            "  TIMESTART TIMESTAMP,\n" +
            "  TIMEEND   TIMESTAMP\n" +
            ");";
}
