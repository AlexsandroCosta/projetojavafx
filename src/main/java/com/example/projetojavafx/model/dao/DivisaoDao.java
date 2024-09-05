package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Divisao;

import java.util.List;

public interface DivisaoDao {
    Divisao procurarPorId(int id);
    List<Divisao> procurarTodos();
}
