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
public class Arceau {
    
    public int id; 
    public double lat;
    public double lng; 

    @Override
    public String toString() {
        return "Arceau{" + "id=" + id + ", lat=" + lat + ", lng=" + lng + '}';
    }

}
