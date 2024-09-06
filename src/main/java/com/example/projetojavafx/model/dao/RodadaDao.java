package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Rodada;

import java.util.List;

public interface RodadaDao {
    void inserir(Rodada r);
    List<Rodada> procurarPorCampeonato(int id_campeonato);
}
