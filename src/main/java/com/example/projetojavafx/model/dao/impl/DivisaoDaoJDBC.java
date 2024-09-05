package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.DivisaoDao;
import com.example.projetojavafx.model.entities.Divisao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DivisaoDaoJDBC implements DivisaoDao {
    private Connection conn;

    public DivisaoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Divisao procurarPorId(int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("select * from Divisao where id_divisao=?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                Divisao d = new Divisao();
                d.setId_divisao(rs.getInt("id_divisao"));
                d.setDivisao(rs.getString("divisao"));

                return d;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultset(rs);
            DB.closeStatement(st);
        }
        return null;
    }

    @Override
    public List<Divisao> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select id_divisao, divisao from Divisao");
            rs = st.executeQuery();
            List<Divisao> lista = new ArrayList<>();

            while(rs.next()){
                Divisao d = new Divisao();
                d.setId_divisao(rs.getInt("id_divisao"));
                d.setDivisao(rs.getString("divisao"));
                lista.add(d);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultset(rs);
            DB.closeStatement(st);
        }
    }
}
