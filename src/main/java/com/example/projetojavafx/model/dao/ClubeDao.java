package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Clube;

import java.util.List;

public interface ClubeDao {
    void inserir(Clube c);
    void atualizar(Clube c);
    void deletarPorId(int id);
    Clube procurarPorId(int id);
    List<Clube> procurarPorTodos();
    List<Clube> procurarPorFiltro(String nome, Integer id_divisao, Integer id_pais);
}
