package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.CampeonatoDao;
import com.example.projetojavafx.model.entities.Campeonato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampeonatoDaoJDBC implements CampeonatoDao {
    private Connection conn = null;

    public CampeonatoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int inserir(Campeonato c) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("insert into Campeonato(nome, id_divisao, ano, data_inicio, data_fim) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, c.getNome());
            st.setInt(2, c.getId_divisao());
            st.setInt(3, c.getAno());
            st.setDate(4, new Date(c.getData_inicio().getTime()));
            st.setDate(5, new Date(c.getData_fim().getTime()));
            st.executeUpdate();

            rs = st.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }else{
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void atualizar(Campeonato c) {

    }

    @Override
    public void deletarPorId(int id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update Campeonato set deletado = true where id_campeonato=?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Campeonato procurarPorId(int id) {
        return null;
    }

    @Override
    public List<Campeonato> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Campeonato where deletado=0");
            rs = st.executeQuery();
            List<Campeonato> lista = new ArrayList<>();

            while(rs.next()){
                Campeonato c = new Campeonato();
                c.setId_campeonato(rs.getInt("id_campeonato"));
                c.setNome(rs.getString("nome"));
                c.setId_divisao(rs.getInt("id_divisao"));
                c.setAno(rs.getInt("ano"));
                c.setData_inicio(rs.getDate("data_inicio"));
                c.setData_fim(rs.getDate("data_fim"));
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

    @Override
    public List<Campeonato> procurarPorFiltro(String nome, Integer id_divisao, Integer ano) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String comando = "select * from Campeonato where 1=1 AND deletado=0";

        if(nome != null && !nome.isEmpty()){
            comando += " AND nome LIKE ?";
        }
        if(id_divisao != null){
            comando += " AND id_divisao = ?";
        }
        if(ano != null){
            comando += " AND ano = ?";
        }

        comando += " order by ano desc, id_divisao";

        try {
            st = conn.prepareStatement(comando);

            int ind = 1;

            if(nome != null && !nome.isEmpty()){
                st.setString(ind++, "%"+ nome + "%");
            }
            if(id_divisao != null){
                st.setInt(ind++, id_divisao);
            }
            if(ano != null){
                st.setInt(ind, ano);
            }

            rs = st.executeQuery();
            List<Campeonato> lista = new ArrayList<>();

            while(rs.next()){
                Campeonato c = new Campeonato();
                c.setId_campeonato(rs.getInt("id_campeonato"));
                c.setNome(rs.getString("nome"));
                c.setId_divisao(rs.getInt("id_divisao"));
                c.setAno(rs.getInt("ano"));
                c.setData_inicio(rs.getDate("data_inicio"));
                c.setData_fim(rs.getDate("data_fim"));
                lista.add(c);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
