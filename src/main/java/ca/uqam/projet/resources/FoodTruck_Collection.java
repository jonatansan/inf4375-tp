/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.resources;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jmilot
 */
public class FoodTruck_Collection {
    public String type;
    public FoodTruck[] features; 

    public FoodTruck_Collection(){
        
    }

    @Override
    public String toString() {
        return "FoodTruck_Collection{" + "type=" + type + ", features=" + Arrays.toString(features) + '}';
    }
    
    
}
