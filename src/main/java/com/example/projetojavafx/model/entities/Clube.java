package com.example.projetojavafx.model.entities;

import java.util.Objects;

public class Clube {
    private int id_clube;
    private int id_pais;
    private int id_divisao;
    private String nome;
    private int deletado;

    public int getId_clube() {
        return id_clube;
    }

    public void setId_clube(int id_clube) {
        this.id_clube = id_clube;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public int getId_divisao() {
        return id_divisao;
    }

    public void setId_divisao(int id_divisao) {
        this.id_divisao = id_divisao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDeletado() {
        return deletado;
    }

    public void setDeletado(int deletado) {
        this.deletado = deletado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return id_clube == clube.id_clube && id_pais == clube.id_pais && id_divisao == clube.id_divisao && Objects.equals(nome, clube.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_clube, id_pais, id_divisao, nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
