package com.inventory_project;

/**
 * An Outsourced part that extends Part
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     *
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max
     *
     * Default constructor for an Outsourced part that extends Part. Separate field, functions for getting/setting companyName.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {return companyName;}
}
