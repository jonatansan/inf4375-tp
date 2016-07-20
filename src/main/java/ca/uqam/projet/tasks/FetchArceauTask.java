/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.tasks;

import ca.uqam.projet.DAO.DAOArceau;
import ca.uqam.projet.resources.Arceau;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author jmilot
 */
@Component
public class FetchArceauTask {

//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 0 1 * *") // Premier de chaque mois
    public void getArceau() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Fetch arceau at : " + dateFormat.format(date));

        String url = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";
        List<Arceau> arceaux = null;
        try {
            InputStream input = new URL(url).openStream();
            arceaux = readArceau(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DAOArceau.clean();
        for (Arceau a : arceaux) {
            DAOArceau.save(a);
        }

    }

    public List<Arceau> readArceau(InputStream in) throws IOException {
        List<Arceau> results = new ArrayList<>();
        TreeMap<String, Integer> fields = new TreeMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String firstLine = br.readLine();

        //Parse header
        String[] fieldsLine = firstLine.split(",");
        for (int i = 0; i < fieldsLine.length; ++i) {
            fields.put(fieldsLine[i], i);
        }

        for (String s : fieldsLine) {
//            System.out.println("s:" + s);
        }

        //Parse the actual CSV file
        String line;
        while ((line = br.readLine()) != null) {

            //Replace those damn ',' between '"'
            Matcher matcher = Pattern.compile("(\"[^\"]*?\")").matcher(line);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, matcher.group(1).replaceAll(",", "."));
            }
            matcher.appendTail(sb);

            fieldsLine = sb.toString().split(",");

//            System.out.println("x:" + fieldsLine[fields.get("EMPL_X")] + " y:" + fieldsLine[fields.get("EMPL_Y")]);
            Arceau a = new Arceau();
            a.lat = Double.parseDouble(fieldsLine[fields.get("LAT")]);
            a.lng = Double.parseDouble(fieldsLine[fields.get("LONG")]);
            results.add(a);
        }

        return results;
    }
}
