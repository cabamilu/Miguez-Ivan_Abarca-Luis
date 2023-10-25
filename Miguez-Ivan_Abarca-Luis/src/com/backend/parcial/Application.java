package com.backend.parcial;

import java.sql.Connection;
import java.sql.DriverManager;

public class Application {
    //https://docs.google.com/document/d/1YOVKL9we1jfbmmZrV9ULov7s1D4eFoxS5YTNMVy8C6A/edit
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
