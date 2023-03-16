package com.inventory_project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The initial application parameters.
 */
public class InventoryApplication extends Application {

    /**
     * @author Ryan Edie.
     *
     * This software has been programmed for ease of use. Not all getters and setters
     * will have commentary on them, and most of my code shares heavy similarities
     * between similar functions in different windows. I've left comments on one
     * function of note of each type.
     */

    /**
     * The single instantiation for the inventory for the entire system.
     */
    public static Inventory masterInventory = new Inventory();

    /**
     * Easiest way to assure a unique ID for each part.
     */
    public static int globalPartId = 0;

    /**
     * Easiest way to assure a unique ProductID for each part.
     */
    public static int globalProductId = 0; //Ditto as above.

    /**
     * @param stage
     * @throws IOException
     * Responsible for loading the FXML for the main screen, and then launching the program.
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) { launch ();}
}

