package com.example.projetojavafx;

import com.example.projetojavafx.model.entities.Campeonato;
import com.example.projetojavafx.model.entities.Partida;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;

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
        System.out.println(gols_casa.getText());
        System.out.println(gols_fora.getText());
        System.out.println(data_partida.getValue());

        if(gols_casa.getText().isEmpty() || gols_fora.getText().isEmpty()){
            if(partida.getGols_casa()!=-1){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Digite placares válidos", ButtonType.OK);
                alert.showAndWait();
            }
        }

        if(data_partida.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Digite uma data válida", ButtonType.OK);
            alert.showAndWait();
        }

    }


}
