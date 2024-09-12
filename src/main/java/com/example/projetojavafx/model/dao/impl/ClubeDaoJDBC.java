package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.ClubeDao;
import com.example.projetojavafx.model.entities.Clube;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubeDaoJDBC implements ClubeDao {
    private Connection conn = null;

    public ClubeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Clube c) {

    }

    @Override
    public void atualizar(Clube c) {

    }

    @Override
    public void deletarPorId(int id) {

    }

    @Override
    public Clube procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Clube where id_clube=?");
            st.setInt(1, id);
            rs = st.executeQuery();

            while(rs.next()){
                Clube c = new Clube();
                c.setNome(rs.getString("nome"));
                return c;
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
    public List<Clube> procurarPorTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Clube");
            rs = st.executeQuery();
            List<Clube> lista = new ArrayList<>();

            while(rs.next()){
                Clube c = new Clube();
                c.setId_clube(rs.getInt("id_clube"));
                c.setId_pais(rs.getInt("id_pais"));
                c.setId_divisao(rs.getInt("id_divisao"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Clube> procurarPorFiltro(String nome, Integer id_divisao, Integer id_pais) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String comando = ("select * from Clube where 1=1");

        if(nome != null && !nome.isEmpty()){
            comando += " AND nome LIKE ?";
        }
        if(id_divisao != null){
            comando += " AND id_divisao = ?";
        }
        if(id_pais != null){
            comando += " AND id_pais = ?";
        }

        try {
            st = conn.prepareStatement(comando);
            int ind = 1;

            if(nome != null && !nome.isEmpty()){
                st.setString(ind++, "%"+ nome + "%");
            }
            if(id_divisao != null){
                st.setInt(ind++, id_divisao);
            }
            if(id_pais != null){
                st.setInt(ind, id_pais);
            }

            rs = st.executeQuery();
            List<Clube> lista = new ArrayList<>();

            while(rs.next()){
                Clube c = new Clube();
                c.setId_clube(rs.getInt("id_clube"));
                c.setId_pais(rs.getInt("id_pais"));
                c.setId_divisao(rs.getInt("id_divisao"));
                c.setNome(rs.getString("nome"));

                lista.add(c);
            }

            return  lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
