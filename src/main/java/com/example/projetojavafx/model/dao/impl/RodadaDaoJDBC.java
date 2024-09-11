package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.RodadaDao;
import com.example.projetojavafx.model.entities.Rodada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            st = conn.prepareStatement("insert into Rodada (id_campeonato, numero) values (?,?)");
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
    public Rodada procurarPorId(int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Rodada where id_rodada=?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Rodada r = new Rodada();
                r.setId_rodada(rs.getInt("id_rodada"));
                r.setId_campeonato(rs.getInt("id_campeonato"));
                r.setNumero(rs.getInt("numero"));

                return r;
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
    public List<Rodada> procurarPorCampeonato(int id_campeonato) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Rodada where id_campeonato=?");
            st.setInt(1, id_campeonato);
            rs = st.executeQuery();
            List<Rodada> lista = new ArrayList<>();

            while(rs.next()){
                Rodada r = new Rodada();
                r.setId_rodada(rs.getInt("id_rodada"));
                r.setId_campeonato(rs.getInt("id_campeonato"));
                r.setNumero(rs.getInt("numero"));

                lista.add(r);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultset(rs);
            DB.closeStatement(st);
        }

    }
}
