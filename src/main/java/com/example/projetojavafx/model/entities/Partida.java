package com.example.projetojavafx.model.entities;

import com.example.projetojavafx.model.dao.DAOFactory;

import java.util.Date;
import java.util.Objects;

public class Partida {
    private int id_partida;
    private int id_rodada;
    private int id_clube_casa;
    private int id_clube_fora;
    private Date data_partida;
    private int gols_casa;
    private int gols_fora;

    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public int getId_rodada() {
        return id_rodada;
    }

    public void setId_rodada(int id_rodada) {
        this.id_rodada = id_rodada;
    }

    public int getId_clube_casa() {
        return id_clube_casa;
    }

    public void setId_clube_casa(int id_clube_casa) {
        this.id_clube_casa = id_clube_casa;
    }

    public int getId_clube_fora() {
        return id_clube_fora;
    }

    public void setId_clube_fora(int id_clube_fora) {
        this.id_clube_fora = id_clube_fora;
    }

    public java.sql.Date getData_partida() {
        return (java.sql.Date) data_partida;
    }

    public void setData_partida(Date data_partida) {
        this.data_partida = data_partida;
    }

    public int getGols_casa() {
        return gols_casa;
    }

    public void setGols_casa(int gols_casa) {
        this.gols_casa = gols_casa;
    }

    public int getGols_fora() {
        return gols_fora;
    }

    public void setGols_fora(int gols_fora) {
        this.gols_fora = gols_fora;
    }

    public String getNomeClube_casa(){
        return DAOFactory.createClubeDao().procurarPorId(id_clube_casa).getNome();
    }

    public String getNomeClubeFora(){
        return DAOFactory.createClubeDao().procurarPorId(id_clube_fora).getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partida partida = (Partida) o;
        return id_partida == partida.id_partida && id_rodada == partida.id_rodada && id_clube_casa == partida.id_clube_casa && id_clube_fora == partida.id_clube_fora && gols_casa == partida.gols_casa && gols_fora == partida.gols_fora && Objects.equals(data_partida, partida.data_partida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_partida, id_rodada, id_clube_casa, id_clube_fora, data_partida, gols_casa, gols_fora);
    }

    @Override
    public String toString() {
        return getNomeClube_casa() + " " + gols_casa + " X " + gols_fora + " " + getNomeClubeFora();
    }
}
