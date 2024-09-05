package com.example.projetojavafx.db;

import java.sql.*;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn==null){
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetojavafx","root","12345678");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeStatement(Statement st){
        if(st!=null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultset(ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
