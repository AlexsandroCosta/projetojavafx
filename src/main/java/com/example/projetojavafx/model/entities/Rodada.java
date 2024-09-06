package com.example.projetojavafx.model.entities;

import java.util.Objects;

public class Rodada {
    private int id_rodada;
    private int id_campeonato;
    private int numero;

    public int getId_rodada() {
        return id_rodada;
    }

    public void setId_rodada(int id_rodada) {
        this.id_rodada = id_rodada;
    }

    public int getId_campeonato() {
        return id_campeonato;
    }

    public void setId_campeonato(int id_campeonato) {
        this.id_campeonato = id_campeonato;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rodada rodada = (Rodada) o;
        return id_rodada == rodada.id_rodada && id_campeonato == rodada.id_campeonato && numero == rodada.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_rodada, id_campeonato, numero);
    }

    @Override
    public String toString() {
        return "Rodada " + numero;
    }
}
