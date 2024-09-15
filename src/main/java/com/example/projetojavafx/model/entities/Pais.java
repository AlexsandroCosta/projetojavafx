package com.example.projetojavafx.model.entities;

import java.util.Objects;

public class Pais {
    private int id_pais;
    private String nome;

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return id_pais == pais.id_pais && Objects.equals(nome, pais.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_pais, nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
