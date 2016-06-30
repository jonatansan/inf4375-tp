/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmilot
 */
@XmlRootElement(name = "station")
public class BixiStation {
    
    private final static double RAYONTERRE = 6371000.0;

    public int id;
    public String name;
    public double lat;
    
    @XmlElement(name = "long")
    public double lng;

    public String installed;
    public String locked;
    public String installDate;
    public String removalDate;
    public String temporary;

    @XmlElement(name = "public")
    public String publicc;

    public int nbBikes;
    public int nbEmptyDocks;

    public BixiStation() {
    }

    @Override
    public String toString() {
        return "BixiStation{" + "id=" + id + ", name=" + name + ", lat=" + lat + ", lng=" + lng + ", installed=" + installed + ", locked=" + locked + ", installDate=" + installDate + ", removalDate=" + removalDate + ", temporary=" + temporary + ", publicc=" + publicc + ", nbBikes=" + nbBikes + ", nbEmptyDocks=" + nbEmptyDocks + '}';
    }
    
    public double distance(double lat, double lng){
        
        double radY = this.lat * Math.PI / 180.0;
        double radX = this.lng * Math.PI / 180.0; 
        
        double coorRadY = lat * Math.PI / 180.0;
        double coorRadX = lng * Math.PI / 180.0; 
        
        double s1 = Math.sin((coorRadY - radY) / 2);
        double s2 = Math.sin((coorRadX - radX) / 2);
        return 2 * RAYONTERRE * Math.asin(Math.sqrt(s1 * s1 + Math.cos(radY) * Math.cos(coorRadY) * s2 * s2));
    }

}
