package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Clube;
import com.example.projetojavafx.model.entities.Divisao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AdicionarCampeonatoController {
    @FXML
    private TextField nome;
    @FXML
    private ComboBox<Divisao> divisao;
    @FXML
    private TextField ano;
    @FXML
    private DatePicker data_inicio;
    @FXML
    private DatePicker data_fim;
    @FXML
    private ListView<Clube> listar_clubes;
    @FXML
    private Button adicionar;

    public void initialize(){
        ObservableList<Clube> items = FXCollections.observableArrayList();

        for(Clube c : DAOFactory.createClubeDao().procurarPorTodos()){
            items.add(c);
        }

        listar_clubes.setItems(items);
        listar_clubes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        divisao.getItems().add(null);
        for(Divisao d : DAOFactory.createDivisaoDao().procurarTodos()){
            divisao.getItems().add(d);
        }
    }

    public void onAdicionarClick(){
        System.out.println(listar_clubes.getSelectionModel().getSelectedItems());
    }
}
