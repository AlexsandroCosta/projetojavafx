package com.example.projetojavafx.model.entities;

import com.example.projetojavafx.model.dao.DAOFactory;

import java.util.Objects;

public class Classificacao {
    private int id_classificacao;
    private int id_campeonato;
    private int id_clube;
    private int pontos;
    private int qtd_partidas;
    private int qtd_vitorias;
    private int qtd_derrotas;
    private int qtd_empates;
    private int qtd_gols_feitos;
    private int qtd_gols_sofridos;

    public String getNomeClube(){
        return DAOFactory.createClubeDao().procurarPorId(id_clube).getNome();
    }

    public int getId_classificacao() {
        return id_classificacao;
    }

    public void setId_classificacao(int id_classificacao) {
        this.id_classificacao = id_classificacao;
    }

    public int getId_campeonato() {
        return id_campeonato;
    }

    public void setId_campeonato(int id_campeonato) {
        this.id_campeonato = id_campeonato;
    }

    public int getId_clube() {
        return id_clube;
    }

    public void setId_clube(int id_clube) {
        this.id_clube = id_clube;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getQtd_partidas() {
        return qtd_partidas;
    }

    public void setQtd_partidas(int qtd_partidas) {
        this.qtd_partidas = qtd_partidas;
    }

    public int getQtd_vitorias() {
        return qtd_vitorias;
    }

    public void setQtd_vitorias(int qtd_vitorias) {
        this.qtd_vitorias = qtd_vitorias;
    }

    public int getQtd_derrotas() {
        return qtd_derrotas;
    }

    public void setQtd_derrotas(int qtd_derrotas) {
        this.qtd_derrotas = qtd_derrotas;
    }

    public int getQtd_empates() {
        return qtd_empates;
    }

    public void setQtd_empates(int qtd_empates) {
        this.qtd_empates = qtd_empates;
    }

    public int getQtd_gols_feitos() {
        return qtd_gols_feitos;
    }

    public void setQtd_gols_feitos(int qtd_gols_feitos) {
        this.qtd_gols_feitos = qtd_gols_feitos;
    }

    public int getQtd_gols_sofridos() {
        return qtd_gols_sofridos;
    }

    public void setQtd_gols_sofridos(int qtd_gols_sofridos) {
        this.qtd_gols_sofridos = qtd_gols_sofridos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classificacao that = (Classificacao) o;
        return id_classificacao == that.id_classificacao && id_campeonato == that.id_campeonato && id_clube == that.id_clube && pontos == that.pontos && qtd_partidas == that.qtd_partidas && qtd_vitorias == that.qtd_vitorias && qtd_derrotas == that.qtd_derrotas && qtd_empates == that.qtd_empates && qtd_gols_feitos == that.qtd_gols_feitos && qtd_gols_sofridos == that.qtd_gols_sofridos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_classificacao, id_campeonato, id_clube, pontos, qtd_partidas, qtd_vitorias, qtd_derrotas, qtd_empates, qtd_gols_feitos, qtd_gols_sofridos);
    }

    @Override
    public String toString() {
        return "Classificacao{" +
                "id_classificacao=" + id_classificacao +
                ", id_campeonato=" + id_campeonato +
                ", id_clube=" + id_clube +
                ", pontos=" + pontos +
                ", qtd_partidas=" + qtd_partidas +
                ", qtd_vitorias=" + qtd_vitorias +
                ", qtd_derrotas=" + qtd_derrotas +
                ", qtd_empates=" + qtd_empates +
                ", qtd_gols_feitos=" + qtd_gols_feitos +
                ", qtd_gols_sofridos=" + qtd_gols_sofridos +
                '}';
    }
}
