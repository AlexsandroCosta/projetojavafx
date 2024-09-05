package com.example.projetojavafx;

import com.example.projetojavafx.model.entities.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Admin admin;
    private static Stage stage;

    @FXML
    private Label bemvindo;
    @FXML
    private Button adicionar_campeonato;

    public void setAdmin(Admin admin){
        this.admin = admin;
    }

    public void initialize(){
        bemvindo.setText("Bem-vindo, " + admin.getUsername());
    }

    public void onAdicionarCampeonatoClick(){
        try {
            stage = Application.newStage("adicionar-campeonato-view.fxml", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
