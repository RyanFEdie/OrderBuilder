package com.inventory_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.inventory_project.InventoryApplication.*;

/**
 * The type Part form controller.
 */
public class PartFormController {

    private Stage partFormStage;
    private String partSource;
    private Part selectedPart;
    private TableView masterPartView;

    /**
     * Gets part form stage.
     *
     * @return the part form stage
     */
    public Stage getPartFormStage() {
        return this.partFormStage;
    }

    /**
     * Sets part form stage.
     *
     * @param stage the stage
     */
    public void setPartFormStage(Stage stage) {
        this.partFormStage = stage;
    }

    /**
     * Sets master part view.
     *
     * @param masterPartView the master part view
     */
    public void setMasterPartView(TableView masterPartView) {
        this.masterPartView = masterPartView;
    }

    /**
     * Gets part source.
     *
     * @return the part source
     */
    public String getPartSource() {
        return this.partSource;
    }

    /**
     * Sets part source.
     *
     * @param s the s
     */
    public void setPartSource(String s) {
        this.partSource = s;
    }

    /**
     * Gets selected part.
     *
     * @return the selected part
     */
    public Part getSelectedPart() {
        return this.selectedPart;
    }

    /**
     * Sets selected part.
     *
     * @param p the p
     */
    public void setSelectedPart(Part p) {
        this.selectedPart = p;
    }


    /**
     * The Cancel part button.
     */
    @FXML
    protected Button cancelPartButton;

    /**
     * On cancel part button press string.
     *
     * @return null. Closes the PartButton window, and discards changes.
     */
    @FXML
    protected String onCancelPartButtonPress() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Press OK to close part screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            getPartFormStage().close();
        } else if (alert.getResult() == ButtonType.CANCEL) {
            return null;
        }
        return null;
    }


    /**
     * The Window type.
     */
    @FXML
    protected Text windowType;

    /**
     * Sets window type.
     *
     * @param str the str
     */
    public void setWindowType(String str) {
        windowType.setText(str);
    }

    /**
     * The Part source toggle.
     */
    @FXML
    protected ToggleGroup partSourceToggle;

    /**
     * The Bottom text.
     */
    @FXML
    protected Text bottomText;

    /**
     * The In house.
     */
    @FXML
    protected RadioButton inHouse;

    /**
     * Sets a partSource string to 'In-House' that determines what happens with the part on Save, and changes the text at the bottom of the window.
     */
    @FXML
    protected void onInHouseToggle() {
        setPartSource("In-House");
        bottomText.setText("Machine ID");
    }

    /**
     * The Outsourced.
     */
    @FXML
    protected RadioButton outsourced;

    /**
     * Sets a partSource string to 'Outsourced' that determines what happens with the part on Save, and changes the text at the bottom of the window.
     */
    @FXML
    protected void onOutsourcedToggle() {
        setPartSource("Outsourced");
        bottomText.setText("Company Name");
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
    public void setQuantityField(Integer i) { quantityField.setText(i.toString()); }
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

    /**
     * The Variable field.
     */
    @FXML
    protected TextField variableField;

    /**
     * The Save part button.
     */
    @FXML
    protected Button savePartButton;

    /**
     *
     *
     * @return null Checks all fields for data correctness, and throws errors if data is placed incorrectly. Checks to see if the part is a fresh part, or a modified one; if the part is a modified part, the relevant setters are called to update the part's fields, and the window is closed. If it is a new part, it's first checked to see if the part is an In-House part, or an Outsourced part- partId is incremented, the part is instantiated and passed the user's input variables, the part is added to the list, and the Part Window is closed.
     */
    @FXML
    protected String onSavePartButtonPress() {
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
        if (selectedPart != null)
        {
            selectedPart.setName(name);
            selectedPart.setPrice(price);
            selectedPart.setStock(quantity);
            selectedPart.setMax(max);
            selectedPart.setMin(min);
            if (selectedPart instanceof InHouse) {
                ((InHouse) selectedPart).setMachineId(Integer.parseInt(variableField.getText()));
            } else {
                ((Outsourced) selectedPart).setCompanyName(variableField.getText());
            }
            selectedPart = null;
            partFormStage.close();
        }
        else {
            if (partSource == "In-House") {
                globalPartId = globalPartId + 1;
                InHouse partInHouse = new InHouse(globalPartId, name, price, quantity, min, max);
                partInHouse.setMachineId(Integer.parseInt(variableField.getText()));
                masterInventory.addPart(partInHouse);
                partFormStage.close();
            } else if (partSource == "Outsourced") {
                globalProductId = globalProductId + 1;
                Outsourced partOutsourced = new Outsourced(globalProductId, name, price, quantity, min, max);
                partOutsourced.setCompanyName(variableField.getText());
                masterInventory.addPart(partOutsourced);
                partFormStage.close();
            }
        }
        masterPartView.refresh();
        return null;
        }

    }


