package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Admin;

public interface AdminDao {
    Admin logar(String username, String password);
}
