module com.example.software_meth_3_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.software_meth_3_javafx to javafx.fxml;
    exports com.example.software_meth_3_javafx;
}