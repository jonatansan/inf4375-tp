/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.controllers;

import ca.uqam.projet.DAO.DAOArceau;
import ca.uqam.projet.DAO.DAOBixis;
import ca.uqam.projet.resources.Arceau;
import ca.uqam.projet.resources.BixiStation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jmilot
 */
@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class ArceauController {

    @RequestMapping(value = "/arceaux", method = RequestMethod.GET)
    public List<Arceau> getArceaux(@RequestParam("lat") double lat, @RequestParam("lng") double lng, HttpServletResponse response) {

        List<Arceau> res = DAOArceau.findFrom200m(lat, lng);        
        return res;
    }

}
