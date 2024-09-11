package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.PaisDao;
import com.example.projetojavafx.model.dao.PartidaDao;
import com.example.projetojavafx.model.entities.Pais;
import com.example.projetojavafx.model.entities.Partida;

import java.sql.*;
import java.util.ArrayList;
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
            st = conn.prepareStatement("insert into Partida (id_rodada, id_clube_casa, id_clube_fora, data_partida) values (?,?,?,?)");
            st.setInt(1, p.getId_rodada());
            st.setInt(2, p.getId_clube_casa());
            st.setInt(3, p.getId_clube_fora());
            st.setDate(4, new Date(p.getData_partida().getTime()));
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Partida p) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update Partida set data_partida=?, gols_casa=?, gols_fora=? where id_partida=?");
            st.setDate(1, (Date) p.getData_partida());
            st.setInt(2, p.getGols_casa());
            st.setInt(3, p.getGols_fora());
            st.setInt(4, p.getId_partida());
            st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Partida> procurarPorRodada(int id_rodada) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Partida where id_rodada=?");
            st.setInt(1, id_rodada);
            rs = st.executeQuery();
            List<Partida> lista = new ArrayList<>();

            while(rs.next()){
                Partida p = new Partida();
                p.setId_partida(rs.getInt("id_partida"));
                p.setId_rodada(rs.getInt("id_rodada"));
                p.setId_clube_casa(rs.getInt("id_clube_casa"));
                p.setId_clube_fora(rs.getInt("id_clube_fora"));
                p.setData_partida(rs.getDate("data_partida"));
                p.setGols_casa(rs.getInt("gols_casa"));
                p.setGols_fora(rs.getInt("gols_fora"));

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
