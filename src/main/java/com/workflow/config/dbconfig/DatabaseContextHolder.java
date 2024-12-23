package com.workflow.config.dbconfig;

public class DatabaseContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static final String MASTER = "MASTER";
    public static final String SLAVE = "SLAVE";

    public static void setDatabaseType(String databaseType) {
        CONTEXT.set(databaseType);
    }

    public static String getDatabaseType() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}