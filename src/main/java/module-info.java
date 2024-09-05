module com.example.projetojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projetojavafx to javafx.fxml;
    exports com.example.projetojavafx;
}