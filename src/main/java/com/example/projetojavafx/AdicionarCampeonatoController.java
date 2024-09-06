package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
    @FXML
    private ProgressBar barrinha;
    @FXML
    private Label infoBarrinha;

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

        barrinha.setVisible(true);
        infoBarrinha.setVisible(true);

        Integer anofinal = anoCampeonato;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    Campeonato campeonato = new Campeonato();
                    campeonato.setNome(nomeCampeonato);
                    campeonato.setId_divisao(divisaoCampeonato.getId_divisao());
                    campeonato.setAno(anofinal);
                    campeonato.setData_inicio(dataInicioConvertida);
                    campeonato.setData_fim(dataFimConvertida);

                    Platform.runLater(()->infoBarrinha.setText("Criando rodadas..."));

                    int id_campeonato = DAOFactory.createCampeonatoDao().inserir(campeonato);

                    int numRodadas = (clubes.size()*2) - 2;
                    int numPartidasPorRodada = clubes.size() / 2;
                    int totalSteps = numRodadas * 2 + numRodadas * numPartidasPorRodada;

                    for(int i=1; i<=numRodadas; i++){
                        Rodada rodada = new Rodada();
                        rodada.setId_campeonato(id_campeonato);
                        rodada.setNumero(i);
                        DAOFactory.createRodadaDao().inserir(rodada);

                        updateProgress(i, totalSteps);
                    }

                    List<Rodada> lista_rodadas = DAOFactory.createRodadaDao().procurarPorCampeonato(id_campeonato);

                    Platform.runLater(()->infoBarrinha.setText("Gerando partidas..."));

                    long diasTotais = ChronoUnit.DAYS.between(dataInicio, dataFim);
                    long intervaloDias = diasTotais / numRodadas ;

                    int n = clubes.size();
                    int currentStep = numRodadas*2;

                    for (int rodada = 0; rodada < numRodadas; rodada++) {
                        for (int i = 0; i < numPartidasPorRodada; i++) {
                            int timeCasa = (rodada + i) % (n - 1);
                            int timeFora = (n - 1 - i + rodada) % (n - 1);

                            if (i == 0) {
                                timeFora = n - 1;
                            }

                            Clube clubeCasa = clubes.get(timeCasa);
                            Clube clubeFora = clubes.get(timeFora);

                            if (clubeCasa != null && clubeFora != null) {
                                Partida partida = new Partida();
                                partida.setId_rodada(lista_rodadas.get(rodada).getId_rodada());
                                partida.setId_clube_casa(clubeCasa.getId_clube());
                                partida.setId_clube_fora(clubeFora.getId_clube());

                                LocalDate dataPartida = dataInicio.plusDays(rodada * intervaloDias);
                                partida.setData_partida(Date.from(dataPartida.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                                DAOFactory.createPartidaDao().inserir(partida);
                            }

                            updateProgress(++currentStep, totalSteps);
                        }
                    }

                    updateProgress(1,1);

                    Platform.runLater(()->{
                        nome.setText(null);
                        divisao.setValue(null);
                        ano.setText(null);
                        data_inicio.setValue(null);
                        data_fim.setValue(null);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campeonato adicionado com sucesso!", ButtonType.OK);
                        alert.showAndWait();
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Platform.runLater(()->barrinha.setVisible(false));
                    Platform.runLater(()->infoBarrinha.setVisible(false));
                }
                return null;
            }
        };

        barrinha.progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
