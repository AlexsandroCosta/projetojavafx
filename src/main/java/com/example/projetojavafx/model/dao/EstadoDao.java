package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Estado;

import java.util.List;

public interface EstadoDao {
    Estado procurarPorId(int id);
    List<Estado> procurarTodos();
}
