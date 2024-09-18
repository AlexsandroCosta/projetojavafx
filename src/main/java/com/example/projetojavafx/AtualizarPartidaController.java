package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Campeonato;
import com.example.projetojavafx.model.entities.Classificacao;
import com.example.projetojavafx.model.entities.Partida;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AtualizarPartidaController {
    private Partida partida;

    public void setPartidaSelecionado(Partida partida){
        this.partida = partida;
    }

    @FXML
    private Label clube_casa;
    @FXML
    private Label clube_fora;
    @FXML
    private TextField gols_casa;
    @FXML
    private TextField gols_fora;
    @FXML
    private DatePicker data_partida;
    @FXML
    private Button atualizar;

    public void initialize(){
        clube_casa.setText(partida.getNomeClube_casa());
        clube_fora.setText(partida.getNomeClubeFora());

        if(partida.getGols_casa() > -1){
            gols_casa.setText(String.valueOf(partida.getGols_casa()));
            gols_fora.setText(String.valueOf(partida.getGols_fora()));
        }

        LocalDate data = ((Date) partida.getData_partida()).toLocalDate();
        data_partida.setValue(data);
    }

    public void onAtualizarClick(){

        if(gols_casa.getText().isEmpty() && !gols_fora.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Digite placares válidos", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        int golsCasa, golsFora;
        try {
            golsCasa = Integer.parseInt(gols_casa.getText());
            golsFora = Integer.parseInt(gols_fora.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "O placa deve ser um número inteiro", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if(!gols_casa.getText().isEmpty() && gols_fora.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Digite placares válidos", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if(gols_casa.getText().isEmpty() || gols_fora.getText().isEmpty()){
            if(partida.getGols_casa()!=-1 && partida.getGols_fora()!=-1){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Digite placares válidos", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        if(data_partida.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Digite uma data válida", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean ocorreu = false;
        int antigo_gols_casa=0 , antigo_gols_fora=0;

        if(partida.getGols_casa()!=-1){
            ocorreu = true;
            antigo_gols_casa = partida.getGols_casa();
            antigo_gols_fora = partida.getGols_fora();
        }

        partida.setGols_casa(golsCasa);
        partida.setGols_fora(golsFora);
        partida.setData_partida(java.sql.Date.valueOf(data_partida.getValue()));

        DAOFactory.createPartidaDao().atualizar(partida);

        int id_campeonato = DAOFactory.createRodadaDao().procurarPorId(partida.getId_rodada()).getId_campeonato();
        int pontos_casa = 0, pontos_fora = 0;

        Classificacao classificacao_casa = DAOFactory.createClassificacaoDao().procurarPorCampeonatoClube(id_campeonato, partida.getId_clube_casa());
        Classificacao classificacao_fora = DAOFactory.createClassificacaoDao().procurarPorCampeonatoClube(id_campeonato, partida.getId_clube_fora());

        if(!ocorreu) {
            classificacao_casa.setQtd_partidas(classificacao_casa.getQtd_partidas() + 1);
            classificacao_fora.setQtd_partidas(classificacao_fora.getQtd_partidas() + 1);
        }else{
            if(antigo_gols_casa == antigo_gols_fora){
                classificacao_casa.setQtd_empates(classificacao_casa.getQtd_empates()-1);
                classificacao_fora.setQtd_empates(classificacao_fora.getQtd_empates()-1);

                classificacao_casa.setPontos(classificacao_casa.getPontos()-1);
                classificacao_fora.setPontos(classificacao_fora.getPontos()-1);
            } else if(antigo_gols_casa>antigo_gols_fora){
                classificacao_casa.setQtd_vitorias(classificacao_casa.getQtd_vitorias()-1);
                classificacao_fora.setQtd_derrotas(classificacao_fora.getQtd_derrotas()-1);

                classificacao_casa.setPontos(classificacao_casa.getPontos()-3);
            }else{
                classificacao_casa.setQtd_derrotas(classificacao_casa.getQtd_derrotas()-1);
                classificacao_fora.setQtd_vitorias(classificacao_fora.getQtd_vitorias()-1);

                classificacao_fora.setPontos(classificacao_fora.getPontos()-3);
            }
        }

        classificacao_casa.setQtd_gols_feitos(classificacao_casa.getQtd_gols_feitos() + golsCasa - antigo_gols_casa);
        classificacao_fora.setQtd_gols_feitos(classificacao_fora.getQtd_gols_feitos() + golsFora - antigo_gols_fora);

        classificacao_casa.setQtd_gols_sofridos(classificacao_casa.getQtd_gols_sofridos() + golsFora - antigo_gols_fora);
        classificacao_fora.setQtd_gols_sofridos(classificacao_fora.getQtd_gols_sofridos() + golsCasa - antigo_gols_casa);

        if(golsCasa == golsFora){
            classificacao_casa.setQtd_empates(classificacao_casa.getQtd_empates()+1);
            classificacao_fora.setQtd_empates(classificacao_fora.getQtd_empates()+1);

            pontos_casa = 1;
            pontos_fora = 1;
        } else if(golsCasa>golsFora){
            classificacao_casa.setQtd_vitorias(classificacao_casa.getQtd_vitorias()+1);
            classificacao_fora.setQtd_derrotas(classificacao_fora.getQtd_derrotas()+1);

            pontos_casa = 3;
        }else{
            classificacao_casa.setQtd_derrotas(classificacao_casa.getQtd_derrotas()+1);
            classificacao_fora.setQtd_vitorias(classificacao_fora.getQtd_vitorias()+1);

            pontos_fora = 3;
        }

        classificacao_casa.setPontos(classificacao_casa.getPontos()+pontos_casa);
        classificacao_fora.setPontos(classificacao_fora.getPontos()+pontos_fora);

        DAOFactory.createClassificacaoDao().atualizar(classificacao_casa);
        DAOFactory.createClassificacaoDao().atualizar(classificacao_fora);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Partida atualizada com sucesso!", ButtonType.OK);
        alert.showAndWait();

    }


}
