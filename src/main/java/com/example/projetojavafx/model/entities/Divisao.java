package com.example.projetojavafx.model.entities;

import java.util.Objects;

public class Divisao {
    private int id_divisao;
    private String divisao;

    public int getId_divisao() {
        return id_divisao;
    }

    public void setId_divisao(int id_divisao) {
        this.id_divisao = id_divisao;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao1 = (Divisao) o;
        return id_divisao == divisao1.id_divisao && Objects.equals(divisao, divisao1.divisao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_divisao, divisao);
    }

    @Override
    public String toString() {
        return divisao;
    }
}
