package com.inventory_project;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

import static com.inventory_project.InventoryApplication.*;

/**
 * The type Main menu controller.
 */
public class MainMenuController {

    @FXML
    private Button exitButton;

    /**
     *
     * @param event
     * Closes the master screen.
     */
    @FXML
    protected void onExitButtonPress(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The Add part.
     */
    @FXML
    protected Button addPart;


    /**
     *
     * @param event
     *
     * Opens a new Part screen, with the expectation that the user will provide all details for a fresh part.
     */
    @FXML
    public void onAddPartButtonPress(ActionEvent event) {
        Pane s = null;
        FXMLLoader loader = new FXMLLoader(InventoryApplication.class.getResource("add-part-form.fxml"));
        try {
            s = loader.load();
        } catch (IOException e) {}
        PartFormController p = loader.getController();
        p.setPartFormStage(new Stage());
        p.getPartFormStage().setScene(new Scene(s));
        p.setWindowType("Add Part");
        p.setPartSource("In-House");
        p.setMasterPartView(partTable);
        p.getPartFormStage().show();
    }

    /**
     * The Modify part button.
     */
    @FXML
    protected Button modifyPart;

    /**
     *
     * @param event
     *
     * Opens a new Part screen, and loads the part the user has selected in the window.
     */
    @FXML
    public void onModifyPartButtonPress(ActionEvent event) {
        Pane s = null;
        FXMLLoader loader = new FXMLLoader(InventoryApplication.class.getResource("add-part-form.fxml"));
        try {
            s = loader.load();
        } catch (IOException e) {}
        PartFormController p = loader.getController();
        p.setPartFormStage(new Stage());
        p.getPartFormStage().setScene(new Scene(s));
        p.setWindowType("Modify Part");
        p.setMasterPartView(partTable);
        p.setSelectedPart(partTable.getSelectionModel().getSelectedItem());
        Part sp = null;
        try {
            sp = partTable.getSelectionModel().getSelectedItem();
            p.setIdField(sp.getId());
            p.setNameField(sp.getName());
            p.setQuantityField(sp.getStock());
            p.setPriceField(sp.getPrice());
            p.setMaxField(sp.getMax());
            p.setMinField(sp.getMin());
            if (sp instanceof Outsourced) {
                p.outsourced.setSelected(true);
                p.setPartSource("Outsourced");
                p.bottomText.setText("Company Name");
                p.variableField.setText(((Outsourced) sp).getCompanyName());
            } else if (sp instanceof InHouse) {
                p.inHouse.setSelected(true);
                p.setPartSource("In-House");
                p.bottomText.setText("Machine ID");
                p.variableField.setText(((Integer) ((InHouse) sp).getMachineId()).toString());
            }
            p.getPartFormStage().show();
        }
        catch (Exception e) {
            p.getPartFormStage().close();
            System.out.println("Please select a part.");
        }
    }

    /**
     * The Delete part button.
     */
    @FXML
    protected Button deletePart;

    /**
     * Deletes the Part the user has selected in the Part Table.
     *
     * @param event
     * @return null string
     */
    public String onDeletePartButtonPress(ActionEvent event) {
        Part p = partTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete that part?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            masterInventory.getAllParts().removeAll(p);
        } else if (alert.getResult() == ButtonType.CANCEL) {
            return null;
        }
        return null;

    }

    /**
     * The Add product.
     */
    @FXML
    protected Button addProduct;

    /**
     * opens a new Product window, with the expectation that the user will provide all data for a fresh Product.
     *
     * @param event
     */
    @FXML
    public void onAddProductButtonPress(ActionEvent event) {
        Pane s = null;
        FXMLLoader loader = new FXMLLoader(InventoryApplication.class.getResource("add-product-form.fxml"));
        try {
            s = loader.load();
        } catch (Exception e) {System.out.println("Could not load FXML. " + e.getClass());}
        ProductFormController p = loader.getController();
        p.setProductFormStage(new Stage());
        p.getProductFormStage().setScene(new Scene(s));
        p.setWindowType("Add Product");
        p.setMasterProductView(productTable);
        p.getProductFormStage().show();

    }

    /**
     * The Modify product.
     */
    @FXML
    protected Button modifyProduct;

    /**
     * RUNTIME ERROR: Nightmare trying to figure this out. Essentially, for about a day and a half,
     * my buttons were opening screens properly, but those windows weren't editable.
     * Turns out that loader.load() instantiates a Controller as denoted in the FXML-
     * the document was loaded, but there was an additional 'new' that was instantiating a new controller, throwing out the controller that came with the loader,
     * and then sets were being performed on a controller that did not have a screen associated with it.
     * This setup is the same for all of the spawn prompt button methods in my project-
     * it just grabs the controller from the Loader object after instantiation.
     */
    @FXML
    public void onModifyProductButtonPress() {
        Pane s = null;
        FXMLLoader loader = new FXMLLoader(InventoryApplication.class.getResource("add-product-form.fxml"));
        try {
            s = loader.load();
        } catch (Exception e) {System.out.println("Could not load FXML. " + e.getClass());}
        ProductFormController p = loader.getController();
        p.setProductFormStage(new Stage());
        p.getProductFormStage().setScene(new Scene(s));
        p.setWindowType("Modify Part");
        p.setMasterProductView(productTable);
        p.setSelectedProduct(productTable.getSelectionModel().getSelectedItem());
        Product sp = null;
        try {
            sp = productTable.getSelectionModel().getSelectedItem();
            p.setAssociatedPartsPicked(sp.getAllAssociatedParts());
            p.assureAssociatedPartsPicked();
            p.setIdField(sp.getId());
            p.setNameField(sp.getName());
            p.setQuantityField(sp.getStock());
            p.setPriceField(sp.getPrice());
            p.setMaxField(sp.getMax());
            p.setMinField(sp.getMin());
            p.getProductFormStage().show();
        }
        catch (Exception e) {
            p.getProductFormStage().close();
            System.out.println("Please select a product.");
        }

    }

    /**
     * The Delete product.
     */
    @FXML
    protected Button deleteProduct;

    /**
     * On delete product button press string.
     *
     * @return null. Deletes the selected Product on the productTable view from the allProducts list.
     */
    @FXML
    public String onDeleteProductButtonPress() {
        Product p = productTable.getSelectionModel().getSelectedItem();
        if (!(p.getAllAssociatedParts().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please clear all part associations with product before deletion.");
            alert.showAndWait();
            return null;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete that product?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            masterInventory.getAllProducts().removeAll(p);
        } else if (alert.getResult() == ButtonType.CANCEL) {
            return null;
        }
        return null;

    }


    //Linking the ObservableList and the TableView together.
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn partIdColumn;
    @FXML
    private TableColumn partNameColumn;
    @FXML
    private TableColumn partInventoryColumn;
    @FXML
    private TableColumn partPriceColumn;
    /**
     * The Search part field.
     */
    @FXML
    protected TextField searchPartField;


    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn productIdColumn;
    @FXML
    private TableColumn productNameColumn;
    @FXML
    private TableColumn productInventoryColumn;
    @FXML
    private TableColumn productPriceColumn;
    /**
     * The Search product field.
     */
    @FXML
    protected TextField searchProductField;


    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));


        /**
         * This is the standard template for my TextField search function. A double lambda
         * that was heavily inspired by Marco Jakob's code at https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/.
         * String manipulation had to be performed so that Name and ID fields could both be searched.
         */
        FilteredList<Part> filteredPart = new FilteredList<>(masterInventory.getAllParts(), p -> true);

        searchPartField.textProperty().addListener((observable, oldValue, newValue) -> { //first Lambda, sets a listener on the field that is used for text input, and updates the tableView whenever a new keystroke is detected
            filteredPart.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; //Show the entire list if the textField is empty.
                    }

                //Check for string matches. First check attempts to cast the search parameter as an int, and then return.
                String lowerCaseFilter = newValue.toLowerCase();
                String numberFilter = newValue;
                if (String.valueOf(part.getId()).contains(numberFilter)){
                    return true; //Filter match on ID.
                } else return part.getName().toLowerCase().contains(lowerCaseFilter); //Filter match on Name.
            });
        });

        SortedList<Part> sortedPartList = new SortedList<>(filteredPart); //Wraps the filteredList in a SortedList for easier viewing

        sortedPartList.comparatorProperty().bind(partTable.comparatorProperty()); //Binding happens here so that the setItems method can be invoked

        partTable.setItems(sortedPartList); //the TableView is provided.






        productIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));


        FilteredList<Product> filteredProduct = new FilteredList<>(masterInventory.getAllProducts(), p -> true);

        searchProductField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProduct.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //Check for string matches. First check attempts to cast the search parameter as an int, and then return.
                String lowerCaseFilter = newValue.toLowerCase();
                String numberFilter = newValue;
                if (String.valueOf(product.getId()).contains(numberFilter)){
                    return true; //Filter match on ID.
                } else return product.getName().toLowerCase().contains(lowerCaseFilter); //Filter match on Name.
            });
        });

        SortedList<Product> sortedProductList = new SortedList<>(filteredProduct);

        sortedProductList.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedProductList);

    }

}