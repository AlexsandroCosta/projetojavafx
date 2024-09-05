package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Pais;

import java.util.List;

public interface PaisDao {
    Pais procurarPorId(int id);
    List<Pais> procurarTodos();
}
