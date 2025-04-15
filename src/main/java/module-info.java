module org.example.ing_sw_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;

    opens org.example.ing_sw_project to javafx.fxml;
    exports org.example.ing_sw_project;
}