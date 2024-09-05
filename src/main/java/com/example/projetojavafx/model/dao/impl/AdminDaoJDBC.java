package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.AdminDao;
import com.example.projetojavafx.model.entities.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoJDBC implements AdminDao {
    private Connection conn = null;

    public AdminDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public Admin logar(String username, String password) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Admin where username=? and password=?");
            st.setString(1, username);
            st.setString(2, password);

            rs = st.executeQuery();

            if(rs.next()){
                Admin a = new Admin();
                a.setId_admin(rs.getInt("id_admin"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));

                return a;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultset(rs);
            DB.closeStatement(st);
        }

        return null;
    }
}
