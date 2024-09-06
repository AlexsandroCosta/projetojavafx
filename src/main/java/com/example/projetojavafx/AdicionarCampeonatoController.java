package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

        if (nomeCampeonato.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira um nome para o campeonato.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (divisaoCampeonato == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, selecione uma divisão.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if(!ano.getText().isEmpty()){
            try{
                anoCampeonato = Integer.parseInt(ano.getText().trim());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira um ano válido.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira um ano para o campeonato.", ButtonType.OK);
            alert.showAndWait();
            return;
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

        if ( dataInicio.getYear() != anoCampeonato) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "O ano do campeonato deve ser o mesmo da data de início.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (clubes.size() < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, selecione pelo menos dois clubes.", ButtonType.OK);
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

        for(int i=1; i<(clubes.size()*2)-1; i++){
            Rodada rodada = new Rodada();
            rodada.setId_campeonato(id_campeonato);
            rodada.setNumero(i);
            DAOFactory.createRodadaDao().inserir(rodada);
        }

        List<Rodada> lista_rodadas = DAOFactory.createRodadaDao().procurarPorCampeonato(id_campeonato);

        long diasTotais = ChronoUnit.DAYS.between(dataInicio, dataFim);
        long intervaloDias = diasTotais / (clubes.size()*2)-2;
        int id_rodada;

        for(int i=0; i<clubes.size(); i++){
            id_rodada = 1;
            for(int j=0; j<clubes.size(); j++){
                if(i!=j){
                    Partida p = new Partida();

                    int rodada;
                    if(i>j){
                        rodada = id_rodada+clubes.size()-2;
                    }else{
                        rodada = id_rodada-1;
                    }
                    //tem time jogando no mesmo dia e o espeço entre as rodadas são de 1 dia TEM Q VER ISSO AI
                    p.setId_rodada(lista_rodadas.get(rodada).getId_rodada());
                    p.setId_clube_casa(clubes.get(i).getId_clube());
                    p.setId_clube_fora(clubes.get(j).getId_clube());

                    LocalDate dataPartida = dataInicio.plusDays(rodada * intervaloDias);
                    p.setData_partida(Date.from(dataPartida.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    DAOFactory.createPartidaDao().inserir(p);

                    if(i<j){
                        id_rodada++;
                    }
                }
            }
        }

        nome.setText(null);
        divisao.setItems(null);
        ano.setText(null);
        data_inicio.setValue(null);
        data_fim.setValue(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campeonato adicionado com sucesso!", ButtonType.OK);
        alert.showAndWait();

    }
}
