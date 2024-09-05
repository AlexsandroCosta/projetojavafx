package com.example.projetojavafx.model.entities;

import java.util.Objects;

public class Admin {
    private int id_admin;
    private String username;
    private String password;

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin){
        this.id_admin = id_admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id_admin == admin.id_admin && Objects.equals(username, admin.username) && Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_admin, username, password);
    }

    @Override
    public String toString() {
        return username;
    }
}
