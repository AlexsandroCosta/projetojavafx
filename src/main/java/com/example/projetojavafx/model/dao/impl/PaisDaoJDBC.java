package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.PaisDao;
import com.example.projetojavafx.model.entities.Divisao;
import com.example.projetojavafx.model.entities.Pais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaisDaoJDBC implements PaisDao {
    private Connection conn;

    public PaisDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public Pais procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Pais where id_pais=?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                Pais p = new Pais();
                p.setId_pais(rs.getInt("id_pais"));
                p.setNome(rs.getString("nome"));

                return p;
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
    public List<Pais> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Pais");
            rs = st.executeQuery();
            List<Pais> lista = new ArrayList<>();

            while(rs.next()){
                Pais p = new Pais();
                p.setId_pais(rs.getInt("id_pais"));
                p.setNome(rs.getString("nome"));

                lista.add(p);
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
