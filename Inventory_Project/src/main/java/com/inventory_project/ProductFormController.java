package com.inventory_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static com.inventory_project.InventoryApplication.*;

/**
 * The type Product form controller.
 */
public class ProductFormController {

    private Stage productFormStage;
    private Product selectedProduct;
    private TableView masterProductView;


    /**
     * Gets product form stage.
     *
     * @return the product form stage
     */
    public Stage getProductFormStage() {
        return this.productFormStage;
    }

    /**
     * Sets product form stage.
     *
     * @param stage the stage
     */
    public void setProductFormStage(Stage stage) {
        this.productFormStage = stage;
    }

    /**
     * Gets window type.
     *
     * @return the window type
     */
    public String getWindowType() {
        return this.windowType.getText();
    }

    /**
     * Sets window type.
     *
     * @param str the str
     */
    public void setWindowType(String str) { windowType.setText(str);}

    /**
     * Sets selected product.
     *
     * @param p the p
     */
    public void setSelectedProduct (Product p) {
        this.selectedProduct = p;
    }

    /**
     * Sets master product view.
     *
     * @param t the t
     */
    public void setMasterProductView (TableView t) {
        this.masterProductView = t;
    }

    /**
     * Sets associated parts picked.
     *
     * @param o the o
     */
    public void setAssociatedPartsPicked (ObservableList o) {
        this.associatedPartsPicked = o;
    }

    /**
     * Assure associated parts picked.
     */
    public void assureAssociatedPartsPicked () {
        partTablePicked.setItems(associatedPartsPicked);
    }

    /**
     * The Window type.
     */
    @FXML
    protected Text windowType;

    /**
     * The Cancel product button.
     */
    @FXML
    protected Button cancelProductButton;

    /**
     * On cancel product button press string.
     *
     * @return null. Default delete.
     */
    @FXML
    protected String onCancelProductButtonPress() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Press OK to close product screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            getProductFormStage().close();
        } else if (alert.getResult() == ButtonType.CANCEL) {
            return null;
        }
        return null;
    }

    /**
     * The Pick selected part button.
     */
    @FXML
    protected Button pickSelectedPartButton;


    /**
     * On pick selected part button press.
     */
    @FXML
    protected void onPickSelectedPartButtonPress() {
            Part p = partTablePicker.getSelectionModel().getSelectedItem();
            associatedPartsPicked.add(p);
    }

    /**
     * The Remove associated part button.
     */
    @FXML
    protected Button removeAssociatedPartButton;

    /**
     * On remove associated part button press.
     */
    @FXML
    protected void onRemoveAssociatedPartButtonPress() {
        partTablePicked.getItems().removeAll(partTablePicked.getSelectionModel().getSelectedItems());
    }


    /**
     * The Id field.
     */
    @FXML
    protected TextField idField; //int

    /**
     * Sets id field.
     *
     * @param i the
     */
    public void setIdField (Integer i) { idField.setText(i.toString()); }
    private int id;

    /**
     * The Name field.
     */
    @FXML
    protected TextField nameField; //String

    /**
     * Sets name field.
     *
     * @param s the s
     */
    public void setNameField (String s) { nameField.setText(s); }
    private String name;

    /**
     * The Quantity field.
     */
    @FXML
    protected TextField quantityField; //stock, renamed for context clarity. int

    /**
     * Sets quantity field.
     *
     * @param i the
     */
    public void setQuantityField(Integer i) { quantityField.setText(i.toString());}
    private int quantity;

    /**
     * The Price field.
     */
    @FXML
    protected TextField priceField; //double

    /**
     * Sets price field.
     *
     * @param d the d
     */
    public void setPriceField(Double d) { priceField.setText(d.toString()); }
    private double price;

    /**
     * The Max field.
     */
    @FXML
    protected TextField maxField; //int

    /**
     * Sets max field.
     *
     * @param i the
     */
    public void setMaxField (Integer i) { maxField.setText(i.toString());}
    private int max;

    /**
     * The Min field.
     */
    @FXML
    protected TextField minField; //int

    /**
     * Sets min field.
     *
     * @param i the
     */
    public void setMinField (Integer i) { minField.setText(i.toString());}
    private int min;

    private ObservableList<Part> associatedPartsPicked = FXCollections.observableArrayList();

    /**
     * The Save product button.
     */
    @FXML
    protected Button saveProductButton;

    /**
     * RUNTIME ERROR @return null for exit purposes. All of my forms have similar logic- first,
     * a series of checks to assure that the variables that are to be set are
     * all of agreeable format to the list I am going to add them to.
     * Next, a check to see if the part/product has already been instantiated
     * (foo != null). If the part has been instantiated, use setters to update the
     * object's fields and close. If not, instantiate a new part, provide parameters,
     * add to the MasterInventory, and then close.
     */
    @FXML
    private String onSaveProductButtonPush () {
        try {
            name = String.valueOf(nameField.getText());
            if (nameField.getLength() == 0) {
                throw new Exception ("Name field can't be empty.");
            }
            price = Double.parseDouble(priceField.getText());
            quantity = Integer.parseInt(quantityField.getText());
            max = Integer.parseInt(maxField.getText());
            min = Integer.parseInt(minField.getText());
            if (min > max) {
                throw new Exception ("Minimum parts must be less than maximum.");
            }
            if (quantity >= max || quantity <= min) {
                throw new Exception ("Part quantity must be between minimum and maximum bounds.");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields correctly. All fields except Name require numbers.");
            alert.setHeaderText("Alert");
            alert.showAndWait();
            return null;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setHeaderText("Alert");
            alert.showAndWait();
            return null;
        }
        if (selectedProduct != null)
        {
            try {
                selectedProduct.setName(name);
                selectedProduct.setStock(quantity);
                selectedProduct.setPrice(price);
                selectedProduct.setMin(min);
                selectedProduct.setMax(max);
                selectedProduct = null;
                productFormStage.close();
            }
            catch (Exception e) {
                System.out.println("Error saving variable: " + e.getMessage());
                return null;
            }
        }
        else {
            Product pr = null;
            try {
                globalProductId = globalProductId + 1;
                pr = new Product(
                        associatedPartsPicked,
                        id = globalProductId,
                        name = nameField.getText(),
                        price = Double.parseDouble(priceField.getText()),
                        quantity = Integer.parseInt(quantityField.getText()),
                        min = Integer.parseInt(minField.getText()),
                        max = Integer.parseInt(maxField.getText())
                );

            } catch (Exception e) {
                globalProductId = globalProductId - 1;
                System.out.println("Could not set variables." + e.getMessage());
            }
            masterInventory.addProduct(pr);
            productFormStage.close();
        }
        masterProductView.refresh();
        return null;
    }

    @FXML
    private TableView<Part> partTablePicker;
    @FXML
    private TableColumn partIdColumn;
    @FXML
    private TableColumn partNameColumn;
    @FXML
    private TableColumn partInventoryColumn;
    @FXML
    private TableColumn partPriceColumn;
    @FXML
    private TextField pickerSearchField;

    @FXML
    private TableView<Part> partTablePicked;
    @FXML
    private TableColumn pickedIdColumn;
    @FXML
    private TableColumn pickedNameColumn;
    @FXML
    private TableColumn pickedInventoryColumn;
    @FXML
    private TableColumn pickedPriceColumn;


    /**
     * Opens a new ProductForm window with the associated JavaFX elements as dictated by the FXML.
     */
    @FXML
    public void initialize() {


        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));



        FilteredList<Part> filteredPartPicker = new FilteredList<>(masterInventory.getAllParts(), p -> true);

        pickerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartPicker.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //Check for string matches. First check attempts to cast the search parameter as an int, and then return.
                String lowerCaseFilter = newValue.toLowerCase();
                String numberFilter = newValue;
                if (String.valueOf(part.getId()).contains(numberFilter)){
                    return true; //Filter match on ID.
                } else return part.getName().toLowerCase().contains(lowerCaseFilter); //Filter match on Name.
            });
        });

        SortedList<Part> sortedPartList = new SortedList<>(filteredPartPicker);

        sortedPartList.comparatorProperty().bind(partTablePicker.comparatorProperty());

        partTablePicker.setItems(sortedPartList);




        pickedIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        pickedNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        pickedInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        pickedPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partTablePicked.setItems(associatedPartsPicked);


    }



}