package com.clinique.gestion_clinique.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// هذا الـ Listener كيراقب Tomcat:
// عند تشغيل التطبيق، يتم تنفيذ contextInitialized لإنشاء Connection Pool.
// وعند إيقاف التطبيق، يتم تنفيذ contextDestroyed لإغلاق جميع الاتصالات بشكل صحيح.
@WebListener
public class DbContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Tomcat يتم تنفيذها فور تشغيل التطبيق فـ 
        DatabaseConfig.initialize();
        System.out.println("====== HikariCP Connection Pool Initialized Successfully ======");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Tomcat يتم تنفيذها عند إيقاف التطبيق أو إيقاف 
        DatabaseConfig.shutdown();
        System.out.println("====== HikariCP Connection Pool Shut Down ======");
    }
}