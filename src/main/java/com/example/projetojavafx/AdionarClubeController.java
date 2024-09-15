package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.ClubeDao;
import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Clube;
import com.example.projetojavafx.model.entities.Divisao;
import com.example.projetojavafx.model.entities.Pais;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdionarClubeController {
    @FXML
    private TextField nome_clube;
    @FXML
    private ComboBox<Divisao> lista_divisoes;
    @FXML
    private ComboBox<Pais> lista_paises;
    @FXML
    private Button adicionar_clube;

    public void initialize(){
        for(Pais p : DAOFactory.createPaisDao().procurarTodos()){
            lista_paises.getItems().add(p);
        }

        for(Divisao d : DAOFactory.createDivisaoDao().procurarTodos()){
            lista_divisoes.getItems().add(d);
        }
    }

    public void onAdicionarClubeClick(ActionEvent event){
        String nome = nome_clube.getText().trim();
        Pais paisSelecionado = lista_paises.getSelectionModel().getSelectedItem();
        Divisao divisaoSelecionada = lista_divisoes.getSelectionModel().getSelectedItem();

        if (nome.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "O nome do clube deve ser fornecido.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (paisSelecionado == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Você deve selecionar um país.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (divisaoSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Você deve selecionar uma divisão.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Clube clube = new Clube();
        clube.setNome(nome);
        clube.setId_pais(paisSelecionado.getId_pais());
        clube.setId_divisao(divisaoSelecionada.getId_divisao());

        DAOFactory.createClubeDao().inserir(clube);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Clube adicionado com sucesso!", ButtonType.OK);
        alerta.showAndWait();

    }
}
