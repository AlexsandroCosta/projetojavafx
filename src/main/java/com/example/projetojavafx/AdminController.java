package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
    @FXML
    private TextField nome_clube1;
    @FXML
    private Label c1;
    @FXML
    private Label c2;
    @FXML
    private Label c3;
    @FXML
    private Button c4;
    @FXML
    private Button c5;
    @FXML
    private ComboBox<Pais> lista_paises;

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

        ObservableList<Pais> paises = FXCollections.observableArrayList();

        for(Pais p : DAOFactory.createPaisDao().procurarTodos()){
            paises.add(p);
        }

        lista_paises.setItems(paises);

        lista_clubes.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount()==1){
                Clube clube_selecionado = lista_clubes.getSelectionModel().getSelectedItem();

                if(clube_selecionado!=null){
                    c1.setVisible(true);
                    c2.setVisible(true);
                    c3.setVisible(true);
                    c4.setVisible(true);
                    c5.setVisible(true);
                    nome_clube1.setVisible(true);
                    lista_paises.setVisible(true);
                    nome_clube1.setText(clube_selecionado.getNome());
                    lista_paises.setValue(DAOFactory.createPaisDao().procurarPorId(clube_selecionado.getId_pais()));
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

    public void onAtualizarClubeClick(){
        String novoNome = nome_clube1.getText();
        Pais novoPais = lista_paises.getValue();

        Clube clube_selecionado = lista_clubes.getSelectionModel().getSelectedItem();

        if (novoNome != null || !novoNome.trim().isEmpty()) {
            for(Clube clube : lista_clubes.getItems()){
                if(clube.getNome().equalsIgnoreCase(novoNome) && clube != clube_selecionado){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Já existe um clube com esse nome.", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }

            clube_selecionado.setNome(novoNome);
        }

        if (novoPais != null) {
            clube_selecionado.setId_pais(novoPais.getId_pais());
        }

        DAOFactory.createClubeDao().atualizar(clube_selecionado);

        ObservableList<Clube> clubes = FXCollections.observableArrayList();
        for(Clube c : DAOFactory.createClubeDao().procurarPorTodos()){
            clubes.add(c);
        }
        lista_clubes.setItems(clubes);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Clube atualizado com sucesso!", ButtonType.OK);
        alert.showAndWait();

    }

    public void onRemoverClubeCLick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Você tem certeza que deseja deletar?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Clube clube_selecionado = lista_clubes.getSelectionModel().getSelectedItem();
            DAOFactory.createClubeDao().deletarPorId(clube_selecionado.getId_clube());

            ObservableList<Clube> clubes = FXCollections.observableArrayList();
            for(Clube c : DAOFactory.createClubeDao().procurarPorTodos()){
                clubes.add(c);
            }
            lista_clubes.setItems(clubes);

            c1.setVisible(false);
            c2.setVisible(false);
            c3.setVisible(false);
            c4.setVisible(false);
            c5.setVisible(false);
            nome_clube1.setVisible(false);
            lista_paises.setVisible(false);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Clube deletado com sucesso!", ButtonType.OK);
            alerta.showAndWait();

        }
    }

    public void onAdicionarClubeClick(){
        try {
            stage = Application.newStage("adicionar-clube-view.fxml", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
