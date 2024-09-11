package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Rodada;

import java.util.List;

public interface RodadaDao {
    void inserir(Rodada r);
    Rodada procurarPorId(int id);
    List<Rodada> procurarPorCampeonato(int id_campeonato);
}
