package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.PaisDao;
import com.example.projetojavafx.model.dao.PartidaDao;
import com.example.projetojavafx.model.entities.Pais;
import com.example.projetojavafx.model.entities.Partida;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PartidaDaoJDBC implements PartidaDao {
    Connection conn = null;

    public PartidaDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void inserir(Partida p) {
        PreparedStatement st = null;;

        try {
            st = conn.prepareStatement("insert into Partida (id_rodada, id_clube_casa, id_clube_fora, data_partida, gols_casa, gols_fora) values (?,?,?,?,?,?)");
            st.setInt(1, p.getId_rodada());
            st.setInt(2, p.getId_clube_casa());
            st.setInt(3, p.getId_clube_fora());
            st.setDate(4, new Date(p.getData_partida().getTime()));
            st.setInt(5, p.getGols_casa());
            st.setInt(6, p.getGols_fora());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Partida p) {

    }

    @Override
    public List<Partida> procurarPorRodada(int id_rodada) {
        return List.of();
    }
}
