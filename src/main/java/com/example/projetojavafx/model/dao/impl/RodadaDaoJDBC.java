package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.RodadaDao;
import com.example.projetojavafx.model.entities.Rodada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RodadaDaoJDBC implements RodadaDao{
    private Connection conn = null;

    public RodadaDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void inserir(Rodada r) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("inser into Rodada (id_campeonato, numero) values (?,?)");
            st.setInt(1, r.getId_campeonato());
            st.setInt(2, r.getNumero());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Rodada> procurarPorTodos() {
        return List.of();
    }
}
