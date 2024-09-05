package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.ClassificacaoDao;
import com.example.projetojavafx.model.entities.Classificacao;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClassificacaoDaoJDBC implements ClassificacaoDao {
    private Connection conn = null;

    public ClassificacaoDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void inserir(Classificacao c) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into Classificacao (id_campeonato, id_clube) values (?,?)");
            st.setInt(1, c.getId_campeonato());
            st.setInt(2, c.getId_clube());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Classificacao c) {

    }

    @Override
    public List<Classificacao> procurarPorCampeonato(int id_campeonato) {
        return List.of();
    }
}
