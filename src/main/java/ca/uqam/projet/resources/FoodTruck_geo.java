/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.resources;

import java.util.Arrays;

/**
 *
 * @author jmilot
 */
public class FoodTruck_geo {
//    "type": "Point",
//        "coordinates": [
//          -73.561254,
//          45.50206
////        ]
    
    public String type;
    public double[] coordinates; 

    public FoodTruck_geo(){
        coordinates = new double[2]; 
    }
    
    @Override
    public String toString() {
        return "FoodTruck_geo{" + "type=" + type + ", coordinates=" + Arrays.toString(coordinates) + '}';
    }

}
