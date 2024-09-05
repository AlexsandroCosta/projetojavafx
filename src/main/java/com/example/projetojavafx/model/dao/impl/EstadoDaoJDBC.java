package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.EstadoDao;
import com.example.projetojavafx.model.entities.Estado;
import com.example.projetojavafx.model.entities.Pais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoDaoJDBC implements EstadoDao {
    private Connection conn;

    public EstadoDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public Estado procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Estado where id_estado=?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                Estado e = new Estado();
                e.setId_estado(rs.getInt("id_estado"));
                e.setNome(rs.getString("nome"));

                return e;
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
    public List<Estado> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Estado");
            rs = st.executeQuery();
            List<Estado> lista = new ArrayList<>();

            while(rs.next()){
                Estado e = new Estado();
                e.setId_estado(rs.getInt("id_estado"));
                e.setNome(rs.getString("nome"));

                lista.add(e);
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
