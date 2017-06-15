package com.floriantoenjes.learning;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

public class DataAccess {
    public static void main(String[] args) throws Exception {
        final Logger logger = Logger.getLogger(DataAccess.class.getName());

        final Properties p = new Properties();
        p.load(new FileInputStream("c:/tmp/onlineshop.properties"));
        Class.forName(p.getProperty("driver"));
        final Connection con = DriverManager.getConnection(p.getProperty("url"),
                p.getProperty("username"), p.getProperty("password"));
        if (con.isValid(10)) {
            logger.info("Connected!");
        }
        logger.info("Closing connection!");
        con.close();
        logger.info("Program finished!");
    }
}
