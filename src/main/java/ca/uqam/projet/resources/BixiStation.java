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
}
