/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.controllers;

import ca.uqam.projet.DAO.DAOFoodTruck;
import ca.uqam.projet.resources.Citation;
import ca.uqam.projet.resources.FoodTruck;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.core.Response;

/**
 *
 * @author jmilot
 */
@RestController
public class FoodTruckController {

    @RequestMapping(value = "/horaires-camions/{debut}/{fin}", method = RequestMethod.GET)
    public List<FoodTruck> findAll(@PathVariable("debut") String debut, @PathVariable("fin") String fin, HttpServletResponse response) {
        System.out.println(debut + " --- " + fin);

        Date dateDebut = null;
        Date dateFin = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            dateDebut = formatter.parse(debut);
            dateFin = formatter.parse(fin);

        } catch (ParseException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        System.out.println(dateDebut.toString() + " " + dateFin);
        List<FoodTruck> result = DAOFoodTruck.getAll();

        for (Iterator<FoodTruck> it = result.iterator(); it.hasNext();) {
            FoodTruck ft = it.next();
            Date ft_date = null;
            try {
                ft_date = formatter.parse(ft.properties.Date);
            } catch (ParseException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }

            if (dateDebut.after(ft_date) || dateFin.before(ft_date)) {
                it.remove();
            }
        }

        return result;
    }
}
