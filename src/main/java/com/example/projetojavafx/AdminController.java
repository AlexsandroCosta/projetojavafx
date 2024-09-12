package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Admin admin;
    private static Stage stage;

    @FXML
    private Button adicionar_campeonato;
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
    private ListView<Clube> lista_clubes;
    @FXML
    private TextField nome_clube;
    @FXML
    private ComboBox<Divisao> divisao_clube;
    @FXML
    private ComboBox<Pais> pais;
    @FXML
    private Button filtrar_clube;

    public void setAdmin(Admin admin){
        this.admin = admin;
    }

    public void initialize(){
        ObservableList<Campeonato> items = FXCollections.observableArrayList();

        for (Campeonato c : DAOFactory.createCampeonatoDao().procurarPorFiltro(null,null,null)){
            items.add(c);
        }

        lista_campeonatos.setItems(items);

        divisao.getItems().add(null);
        divisao_clube.getItems().add(null);
        for(Divisao d : DAOFactory.createDivisaoDao().procurarTodos()){
            divisao.getItems().add(d);
            divisao_clube.getItems().add(d);
        }

        pais.getItems().add(null);
        for(Pais p : DAOFactory.createPaisDao().procurarTodos()){
            pais.getItems().add(p);
        }

        lista_campeonatos.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() == 2){
                Campeonato item_selecionado = lista_campeonatos.getSelectionModel().getSelectedItem();

                if (item_selecionado != null){
                    try {
                        AdminCampeonatoController controller = new AdminCampeonatoController();
                        controller.setCampeonatoSelecionado(item_selecionado);

                        stage = Application.newStage("admin-campeonato-view.fxml", controller);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

        ObservableList<Clube> clubes = FXCollections.observableArrayList();

        for(Clube c : DAOFactory.createClubeDao().procurarPorTodos()){
            clubes.add(c);
        }

        lista_clubes.setItems(clubes);

        lista_clubes.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount()==2){
                Clube clube_selecionado = lista_clubes.getSelectionModel().getSelectedItem();

                if(clube_selecionado!=null){
                    System.out.println(clube_selecionado);
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

    public void onAdicionarCampeonatoClick(){
        try {
            stage = Application.newStage("adicionar-campeonato-view.fxml", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onFiltrarClubesClick(){
        String nomeClube = nome_clube.getText().trim();
        Divisao divisaoFiltro = divisao_clube.getValue();
        Pais paisFiltro = pais.getValue();

        ObservableList<Clube> items = FXCollections.observableArrayList();

        for(Clube c : DAOFactory.createClubeDao().procurarPorFiltro(
                nomeClube.isEmpty()?null : nomeClube,
                divisaoFiltro!=null?divisaoFiltro.getId_divisao():null,
                paisFiltro!=null?paisFiltro.getId_pais():null)){
            items.add(c);
        }

        lista_clubes.setItems(items);
    }

}
