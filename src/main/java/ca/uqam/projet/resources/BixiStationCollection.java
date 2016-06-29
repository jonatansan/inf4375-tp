/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.resources;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmilot
 */
@XmlRootElement(name ="stations")
public class BixiStationCollection {
    public List<BixiStation> station = new LinkedList<>(); 

    public BixiStationCollection() {
    }
    
    @Override
    public String toString() {
        return "BixiStationCollection{" + "station=" + station + '}';
    }
    
    
}
