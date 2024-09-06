package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Campeonato;
import com.example.projetojavafx.model.entities.Clube;
import com.example.projetojavafx.model.entities.Divisao;
import com.example.projetojavafx.model.entities.Rodada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        List<Clube> clubes = listar_clubes.getSelectionModel().getSelectedItems();
        String nomeCampeonato = nome.getText().trim();
        Divisao divisaoCampeonato = divisao.getValue();
        Integer anoCampeonato = null;

        if(!ano.getText().isEmpty()){
            try{
                anoCampeonato = Integer.parseInt(ano.getText().trim());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira um ano válido.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        LocalDate dataInicio = data_inicio.getValue();
        LocalDate dataFim = data_fim.getValue();

        if (dataInicio == null || dataFim == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, selecione as datas de início e fim.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Date dataInicioConvertida = Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFimConvertida = Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (dataInicioConvertida.after(dataFimConvertida)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A data de início deve ser anterior à data de fim.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Campeonato campeonato = new Campeonato();
        campeonato.setNome(nomeCampeonato);
        campeonato.setId_divisao(divisaoCampeonato.getId_divisao());
        campeonato.setAno(anoCampeonato);
        campeonato.setData_inicio(dataInicioConvertida);
        campeonato.setData_fim(dataFimConvertida);

        int id_campeonato = DAOFactory.createCampeonatoDao().inserir(campeonato);

        for(int i=1; i<clubes.size(); i++){
            Rodada rodada = new Rodada();
            rodada.setId_campeonato(id_campeonato);
            rodada.setNumero(i);
            DAOFactory.createRodadaDao().inserir(rodada);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campeonato adicionado com sucesso!", ButtonType.OK);
        alert.showAndWait();

    }
}
