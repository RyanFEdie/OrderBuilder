package com.inventory_project;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The class that contains both the Part and Product lists for the entire project, as well as associated Getters and Setters.
 */
public class Inventory {

    /**
     * FUTURE ENHANCEMENT: Write and load these lists to disk on system shutdown. Part list for the inventory, created when an Inventory is instantiated.
     */
    private final ObservableList<Part> allParts;

    /**
     * FUTURE ENHANCEMENT: Write and load to disk on system start and shutdown.
     */
    private final ObservableList<Product> allProducts;

    public void addPart(Part part) {allParts.add(part);}

    public void addProduct(Product product) {allProducts.add(product);}

    /**
     * @param partId
     * @return
     * Lookup in allParts list for the Part class, meant to be used in the Part table. Replaced in practice by a lambda.
     */
    public Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
        if (part.getId() == partId) {
            return part;
        }
        else {
            System.out.println("Part not found.");
        }
        }
        return null;
    }

    /**
     * @param productId
     * @return
     * Lookup in allParts list for the Part class, meant to be used in the Part table. Replaced in practice by a lambda.
     */
    public Product lookupProduct(int productId) {
        for (Product product : getAllProducts()){
        if (product.getId() == productId) {
            return product;
        }
        else {
            System.out.println("Product not found."); //TODO:Replace with an error box
        }
        }
        return null;
    }

    public void updatePart(int index, Part selectedPart) {}

    public void updateProduct(int index, Product newProduct){}

    public boolean deletePart(Part selectedPart) {allParts.remove(selectedPart); return true;}

    public boolean deleteProduct(Product selectedProduct) {allProducts.remove(selectedProduct); return true;}

    /**
     * @return allParts
     */
    public ObservableList<Part> getAllParts() {return allParts;}

    /**
     * @return allProducts
     */
    public ObservableList<Product> getAllProducts() {return allProducts;}

    /**
     * Default constructor that wraps the ObservableLists for allParts and allProducts in an ObservableArrayList, with extra utilites.
     */
    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }
}
