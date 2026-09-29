package com.clinique.gestion_clinique.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfig {

    private static HikariDataSource dataSource;

    // 1. عند تشغيل التطبيق Connection Pool إعداد الـ  
    public static void initialize() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            
            // معلومات قاعدة البيانات
            config.setJdbcUrl("jdbc:mysql://localhost:3306/gestion_clinique?useSSL=false&serverTimezone=UTC");
            config.setUsername("root");
            config.setPassword(""); 
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Pool (Optimization)إعدادات الـ 
            config.setMaximumPoolSize(10);        // أقصى عدد اتصالات مفتوحة
            config.setMinimumIdle(2);             // أقل عدد اتصالات جاهزة تنتظر
            config.setIdleTimeout(30000);         // (ms)وقت الانتظار قبل إغلاق الاتصال غير المستعمل 
            config.setConnectionTimeout(20000);   // أقصى وقت ينتظره الطلب للحصول على اتصال

            dataSource = new HikariDataSource(config);
        }
    }

    // 2.DAO / Service دالة إرجاع الاتصال لاستعمالها فـ 
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initialize();
        }
        return dataSource.getConnection();
    }

    // 3. Tomcatعند إيقاف Pool إغلاق الـ 
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}