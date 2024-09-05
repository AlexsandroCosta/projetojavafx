package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Campeonato;

import java.util.List;

public interface CampeonatoDao {
    void inserir(Campeonato c);
    void atualizar(Campeonato c);
    void deletarPorId(int id);
    Campeonato procurarPorId(int id);
    List<Campeonato> procurarTodos();
    List<Campeonato> procurarPorFiltro(String nome, Integer id_divisao, Integer ano);
}
