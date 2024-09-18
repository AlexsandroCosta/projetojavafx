package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Campeonato;
import com.example.projetojavafx.model.entities.Classificacao;
import com.example.projetojavafx.model.entities.Partida;
import com.example.projetojavafx.model.entities.Rodada;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class AdminCampeonatoController {
    private Campeonato campeonato;
    private static Stage stage;

    public void setCampeonatoSelecionado(Campeonato campeonato){
        this.campeonato = campeonato;
    }

    @FXML
    private Label titulo;
    @FXML
    private TableView<Classificacao> tabela;
    @FXML
    private TableColumn<Classificacao, Integer> posicaoColumn;
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
    @FXML
    private Button atualizar_classificacao;
    @FXML
    private Button remover_campeonato;

    public void initialize(){
        titulo.setText(campeonato.toString());

        posicaoColumn.setCellValueFactory(cellData -> {
            ObservableList<Classificacao> classificacao = getClassificacao();
            classificacao.sort(Comparator.comparingInt(Classificacao::getPontos).reversed());

            int posicao = classificacao.indexOf(cellData.getValue()) + 1;
            return new ReadOnlyObjectWrapper<>(posicao);
        });
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

                    lista_partidas.setOnMouseClicked((MouseEvent event2) -> {
                        if(event2.getClickCount()==2){
                            Partida partida_selecionada = lista_partidas.getSelectionModel().getSelectedItem();

                            if(partida_selecionada!=null){
                                AtualizarPartidaController controller = new AtualizarPartidaController();
                                controller.setPartidaSelecionado(partida_selecionada);

                                try {
                                    stage = Application.newStage("atualizar-partida-view.fxml", controller);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void onAtualizarClassificacaoClick(){
        tabela.setItems(getClassificacao());
    }

    private ObservableList<Classificacao> getClassificacao(){
        ObservableList<Classificacao> classificacao = FXCollections.observableArrayList();

        for(Classificacao c: DAOFactory.createClassificacaoDao().procurarPorCampeonato(campeonato.getId_campeonato())){
            classificacao.add(c);
        }

        return classificacao;
    }

    public void onRemoverCampeonatoClick(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Você tem certeza que deseja deletar?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DAOFactory.createCampeonatoDao().deletarPorId(campeonato.getId_campeonato());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
