package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Classificacao;

import java.util.List;

public interface ClassificacaoDao {
    void inserir(Classificacao c);
    void atualizar(Classificacao c);
    Classificacao procurarPorCampeonatoClube(int id_campeonato, int id_clube);
    List<Classificacao> procurarPorCampeonato(int id_campeonato);
}
