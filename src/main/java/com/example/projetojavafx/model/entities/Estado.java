package com.example.projetojavafx.model.entities;

import java.util.Objects;

public class Estado {
    private int id_estado;
    private String nome;

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
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
        Estado estado = (Estado) o;
        return id_estado == estado.id_estado && Objects.equals(nome, estado.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_estado, nome);
    }

    @Override
    public String toString() {
        return "Estado{" +
                "id_estado=" + id_estado +
                ", nome='" + nome + '\'' +
                '}';
    }
}
