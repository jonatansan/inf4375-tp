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
public class Distance {
    
    private final static double RAYONTERRE = 6371000.0;
    
    public static double inMeter(double lat1, double lng1, double lat2, double lng2){
        
        double radY = lat2 * Math.PI / 180.0;
        double radX = lng2 * Math.PI / 180.0; 
        
        double coorRadY = lat1 * Math.PI / 180.0;
        double coorRadX = lng1 * Math.PI / 180.0; 
        
        double s1 = Math.sin((coorRadY - radY) / 2);
        double s2 = Math.sin((coorRadX - radX) / 2);
        return 2 * RAYONTERRE * Math.asin(Math.sqrt(s1 * s1 + Math.cos(radY) * Math.cos(coorRadY) * s2 * s2));
    }
}
