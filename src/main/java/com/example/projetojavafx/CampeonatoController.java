package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Campeonato;
import com.example.projetojavafx.model.entities.Classificacao;
import com.example.projetojavafx.model.entities.Partida;
import com.example.projetojavafx.model.entities.Rodada;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CampeonatoController {
    private Campeonato campeonato;

    @FXML
    private Label titulo;
    @FXML
    private TableView<Classificacao> tabela;
    @FXML
    private TableColumn<Classificacao, String> clubeColumn;
    @FXML
    private TableColumn<Classificacao, Integer> pontosColumn;
    @FXML
    private TableColumn<Classificacao, Integer> partidasColumn;
    @FXML
    private TableColumn<Classificacao, Integer> vitoriasColumn;
    @FXML
    private TableColumn<Classificacao, Integer> empatesColumn;
    @FXML
    private TableColumn<Classificacao, Integer> derrotasColumn;
    @FXML
    private TableColumn<Classificacao, Integer> golsFeitosColumn;
    @FXML
    private TableColumn<Classificacao, Integer> golsSofridosColumn;
    @FXML
    private TableColumn<Classificacao, Integer> saldoGolColumn;
    @FXML
    private ListView<Rodada> lista_rodadas;
    @FXML
    private ListView<Partida> lista_partidas;

    public void setCampeonatoSelecionado(Campeonato campeonato){
        this.campeonato = campeonato;
    }

    public void initialize(){
        titulo.setText(campeonato.toString());

        clubeColumn.setCellValueFactory(cellData ->
            new ReadOnlyObjectWrapper<>(cellData.getValue().getNomeClube())
        );
        pontosColumn.setCellValueFactory(new PropertyValueFactory<>("pontos"));
        partidasColumn.setCellValueFactory(new PropertyValueFactory<>("qtd_partidas"));
        vitoriasColumn.setCellValueFactory(new PropertyValueFactory<>("qtd_vitorias"));
        empatesColumn.setCellValueFactory(new PropertyValueFactory<>("qtd_empates"));
        derrotasColumn.setCellValueFactory(new PropertyValueFactory<>("qtd_derrotas"));
        golsFeitosColumn.setCellValueFactory(new PropertyValueFactory<>("qtd_gols_feitos"));
        golsSofridosColumn.setCellValueFactory(new PropertyValueFactory<>("qtd_gols_sofridos"));
        saldoGolColumn.setCellValueFactory(cellData ->
            new ReadOnlyObjectWrapper<>(cellData.getValue().getQtd_gols_feitos() - cellData.getValue().getQtd_gols_sofridos())
        );
        tabela.setItems(getClassificacao());

        ObservableList<Rodada> rodadas = FXCollections.observableArrayList();

        for(Rodada r : DAOFactory.createRodadaDao().procurarPorCampeonato(campeonato.getId_campeonato())){
            rodadas.add(r);
        }

        lista_rodadas.setItems(rodadas);

        lista_rodadas.setOnMouseClicked((MouseEvent event) ->{
            if(event.getClickCount()==2){
                Rodada rodada = lista_rodadas.getSelectionModel().getSelectedItem();

                if(rodada != null){
                    ObservableList<Partida> partidas = FXCollections.observableArrayList();

                    for(Partida p : DAOFactory.createPartidaDao().procurarPorRodada(rodada.getId_rodada())){
                        partidas.add(p);
                    }

                    lista_partidas.setItems(partidas);
                }
            }
        });
    }

    private ObservableList<Classificacao> getClassificacao(){
        ObservableList<Classificacao> classificacao = FXCollections.observableArrayList();

        for(Classificacao c: DAOFactory.createClassificacaoDao().procurarPorCampeonato(campeonato.getId_campeonato())){
            classificacao.add(c);
        }

        return classificacao;
    }


}
