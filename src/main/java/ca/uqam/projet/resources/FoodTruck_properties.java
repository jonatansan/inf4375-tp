/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.resources;

import java.util.Date;

/**
 *
 * @author jmilot
 */
public class FoodTruck_properties {
    public String name;
    public String description;
    public String Date; 
    public String Heure_debut;
    public String Heure_fin;
    public String Lieu; 
    public String Camion; 
    public String Truckid; 

    public FoodTruck_properties(){
        
    }

    @Override
    public String toString() {
        return "FoodTruck_properties{" + "name=" + name + ", description=" + description + ", Date=" + Date + ", Heure_debut=" + Heure_debut + ", Heure_fin=" + Heure_fin + ", Lieu=" + Lieu + ", Camion=" + Camion + ", Truckid=" + Truckid + '}';
    }
    
    
}
