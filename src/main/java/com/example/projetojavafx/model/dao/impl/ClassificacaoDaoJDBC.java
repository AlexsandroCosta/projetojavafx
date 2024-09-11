package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.ClassificacaoDao;
import com.example.projetojavafx.model.entities.Classificacao;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update Classificacao set pontos=?, " +
                    "qtd_partidas=?, qtd_vitorias=?, qtd_derrotas=?, qtd_empates=?," +
                    "qtd_gols_feitos=?, qtd_gols_sofridos=? where id_classificacao=?");
            st.setInt(1, c.getPontos());
            st.setInt(2, c.getQtd_partidas());
            st.setInt(3, c.getQtd_vitorias());
            st.setInt(4, c.getQtd_derrotas());
            st.setInt(5, c.getQtd_empates());
            st.setInt(6, c.getQtd_gols_feitos());
            st.setInt(7, c.getQtd_gols_sofridos());
            st.setInt(8, c.getId_classificacao());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Classificacao procurarPorCampeonatoClube(int id_campeonato, int id_clube) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Classificacao where id_campeonato=? AND id_clube=?");
            st.setInt(1, id_campeonato);
            st.setInt(2, id_clube);
            rs = st.executeQuery();

            if(rs.next()){
                Classificacao c = new Classificacao();
                c.setId_classificacao(rs.getInt("id_classificacao"));
                c.setId_campeonato(rs.getInt("id_campeonato"));
                c.setId_clube(rs.getInt("id_clube"));
                c.setPontos(rs.getInt("pontos"));
                c.setQtd_partidas(rs.getInt("qtd_partidas"));
                c.setQtd_vitorias(rs.getInt("qtd_vitorias"));
                c.setQtd_derrotas(rs.getInt("qtd_derrotas"));
                c.setQtd_empates(rs.getInt("qtd_empates"));
                c.setQtd_gols_feitos(rs.getInt("qtd_gols_feitos"));
                c.setQtd_gols_sofridos(rs.getInt("qtd_gols_sofridos"));

                return c;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Classificacao> procurarPorCampeonato(int id_campeonato) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Classificacao where id_campeonato=? order by pontos DESC, (qtd_gols_feitos - qtd_gols_sofridos) DESC");
            st.setInt(1, id_campeonato);
            rs = st.executeQuery();
            List<Classificacao> lista = new ArrayList<>();

            while(rs.next()){
                Classificacao c = new Classificacao();
                c.setId_classificacao(rs.getInt("id_classificacao"));
                c.setId_campeonato(rs.getInt("id_campeonato"));
                c.setId_clube(rs.getInt("id_clube"));
                c.setPontos(rs.getInt("pontos"));
                c.setQtd_vitorias(rs.getInt("qtd_partidas"));
                c.setQtd_derrotas(rs.getInt("qtd_derrotas"));
                c.setQtd_empates(rs.getInt("qtd_empates"));
                c.setQtd_gols_feitos(rs.getInt("qtd_gols_feitos"));
                c.setQtd_gols_sofridos(rs.getInt("qtd_gols_sofridos"));

                lista.add(c);
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
