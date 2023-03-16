package com.inventory_project;

/**
 * An Outsourced part that extends Part
 */
public class InHouse extends Part {

    /**
     * Unique identifier for the InHouse class.
     */
    private int machineId;

    /**
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     *
     * Constructor for the InHouse object type that extends the Part class.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }
    public void setMachineId(int machineId) {this.machineId = machineId;}

    public int getMachineId() {
        return machineId;
    }
}
