package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Admin;
import com.example.projetojavafx.model.entities.Campeonato;
import com.example.projetojavafx.model.entities.Divisao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController {
    private static Stage stage;

    @FXML
    private ListView<Campeonato> lista_campeonatos;

    @FXML
    private TextField nome;
    @FXML
    private ComboBox<Divisao> divisao;
    @FXML
    private TextField ano;
    @FXML
    private Button filtrar;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button entrar;

    public void initialize(){
        ObservableList<Campeonato> items = FXCollections.observableArrayList();

        for (Campeonato c : DAOFactory.createCampeonatoDao().procurarPorFiltro(null,null,null)){
            items.add(c);
        }

        lista_campeonatos.setItems(items);

        divisao.getItems().add(null);
        for(Divisao d : DAOFactory.createDivisaoDao().procurarTodos()){
            divisao.getItems().add(d);
        }

        lista_campeonatos.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() == 2){
                Campeonato item_selecionado = lista_campeonatos.getSelectionModel().getSelectedItem();

                if (item_selecionado != null){
                    try {
                        CampeonatoController campeonatoController = new CampeonatoController();
                        campeonatoController.setCampeonatoSelecionado(item_selecionado);

                        stage = Application.newStage("campeonato-view.fxml", campeonatoController);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //System.out.println("Item selecionado: " + item_selecionado.getId_campeonato());
                }
            }
        });
    }

    public void onFiltrarClick(){
        String nomeFiltro = nome.getText().trim();
        Divisao divisaoFiltro = divisao.getValue();
        Integer anoFiltro = null;

        if(!ano.getText().isEmpty()){
            try{
                anoFiltro = Integer.parseInt(ano.getText().trim());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira um ano válido.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        ObservableList<Campeonato> items = FXCollections.observableArrayList();

        for(Campeonato c : DAOFactory.createCampeonatoDao().procurarPorFiltro(
                nomeFiltro.isEmpty() ? null : nomeFiltro,
                divisaoFiltro!=null ? divisaoFiltro.getId_divisao() : null,
                anoFiltro)){

            items.add(c);
        }

        lista_campeonatos.setItems(items);

    }

    public void onEntrarClick(){
        String usernameAdmin = username.getText().trim();
        String passwordAdmin = password.getText().trim();

        Admin admin = DAOFactory.createAdminDao().logar(usernameAdmin, passwordAdmin);

        if(admin!=null){
            try {
                AdminController adminController = new AdminController();
                adminController.setAdmin(admin);

                username.setText("");
                password.setText("");

                stage = Application.newStage("admin-view.fxml", adminController);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username ou password inválidos.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }

}