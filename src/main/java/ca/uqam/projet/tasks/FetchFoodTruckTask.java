/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.CitationRepository;
import ca.uqam.projet.resources.FoodTruck;
import ca.uqam.projet.resources.FoodTruck_Collection;
import ca.uqam.projet.DAO.DAOFoodTruck;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author jmilot
 */
@Component
public class FetchFoodTruckTask {
    
    @Scheduled(fixedRate = 50000)
//    @Scheduled(cron = "0 0 0/12 * * *")
    public void getFoodTruck() {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); 
        
        RestTemplate restTemplate = new RestTemplate();
        FoodTruck_Collection foodTruck_collection = restTemplate.getForObject("http://camionderue.com/donneesouvertes/geojson", FoodTruck_Collection.class);
        DAOFoodTruck.clearFoodTruck();
        for(FoodTruck ft : foodTruck_collection.features){
            DAOFoodTruck.saveFoodTruck(ft); 
        }
    }

}
