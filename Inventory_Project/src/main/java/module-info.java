module com.inventory_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.inventory_project to javafx.fxml;
    exports com.inventory_project;
}