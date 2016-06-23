/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.resources;

/**
 *
 * @author jmilot
 */

public class FoodTruck {
    public String type; 
    public FoodTruck_properties properties; 
    public FoodTruck_geo geometry;

    public FoodTruck() {
        properties = new FoodTruck_properties();
        geometry = new FoodTruck_geo(); 
    }

    @Override
    public String toString() {
        return "FoodTruck{" + "type=" + type + ", properties=" + properties + ", geometry=" + geometry + '}';
    }
     
}
