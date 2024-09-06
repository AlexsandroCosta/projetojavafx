package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Partida;

import java.util.List;

public interface PartidaDao {
    void inserir(Partida p);
    void atualizar(Partida p);
    List<Partida> procurarPorRodada(int id_rodada);
}
