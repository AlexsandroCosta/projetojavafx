package com.example.projetojavafx.model.entities;

import com.example.projetojavafx.model.dao.DAOFactory;

import java.util.Date;
import java.util.Objects;

public class Campeonato {
    private int id_campeonato;
    private String nome;
    private int id_divisao;
    private int ano;
    private Date data_inicio;
    private Date data_fim;

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " " + ano + " - Divisão " + DAOFactory.createDivisaoDao().procurarPorId(id_divisao).getDivisao();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_campeonato() {
        return id_campeonato;
    }

    public void setId_campeonato(int id_campeonato) {
        this.id_campeonato = id_campeonato;
    }

    public int getId_divisao() {
        return id_divisao;
    }

    public void setId_divisao(int id_divisao) {
        this.id_divisao = id_divisao;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Date getData_fim() {
        return data_fim;
    }

    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campeonato that = (Campeonato) o;
        return id_campeonato == that.id_campeonato && id_divisao == that.id_divisao && ano == that.ano && Objects.equals(nome, that.nome) && Objects.equals(data_inicio, that.data_inicio) && Objects.equals(data_fim, that.data_fim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_campeonato, nome, id_divisao, ano, data_inicio, data_fim);
    }
}
