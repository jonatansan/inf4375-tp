/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.tasks;

import ca.uqam.projet.DAO.DAOBixis;
import ca.uqam.projet.resources.BixiStation;
import ca.uqam.projet.resources.BixiStationCollection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author jmilot
 */
@Component
public class FetchBixiTask {

//    @Scheduled(fixedRate = 1000)
    @Scheduled(cron = "0 */10 * * * *")
    public void getBixi() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Fetch Bixi at : " + dateFormat.format(date));

        String url = "https://montreal.bixi.com/data/bikeStations.xml";

        RestTemplate restTemplate = new RestTemplate();
        BixiStationCollection bixis = restTemplate.getForObject(url, BixiStationCollection.class);
        DAOBixis.clearBixi();
        for (BixiStation b : bixis.station) {
            DAOBixis.save(b);
        }
    }

}
