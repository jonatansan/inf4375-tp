/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.DAO;

import ca.uqam.projet.Application;
import ca.uqam.projet.resources.FoodTruck;
import ca.uqam.projet.resources.FoodTruck_Collection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author jmilot
 */
public class DAOFoodTruck {

    public static List<FoodTruck> getAll() {

        List<FoodTruck> result = new LinkedList<>();

        Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *, ST_X(coordinate) as coorX,  ST_Y(coordinate) as coorY from foodtruck"); 
            
            while(rs.next()){
                FoodTruck ft = new FoodTruck();
                
                ft.geometry.coordinates[0] = rs.getDouble("coorY"); 
                ft.geometry.coordinates[1] = rs.getDouble("coorX");
                ft.properties.Camion = rs.getString("Camion");
                ft.properties.Date = rs.getString("datePresent");
                ft.properties.Heure_debut = rs.getString("Heure_debut");
                ft.properties.Heure_fin = rs.getString("Heure_fin");
                ft.properties.Lieu = rs.getString("Lieu");
                ft.properties.Truckid = rs.getString("Truckid");
                ft.properties.description = rs.getString("description");
                ft.properties.name = rs.getString("name");
                result.add(ft); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.dbConnectionPool.releaseConnection(con);
        return result;
    }

    public static void saveFoodTruck(FoodTruck ft) {

        Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();

            st.executeUpdate("INSERT INTO foodtruck (coordinate, name, datePresent, Heure_debut, Heure_fin, Lieu, Camion, truckid, description) "
                    + " VALUES "
                    + " (ST_SetSRID(ST_MakePoint(" + ft.geometry.coordinates[1] + ", " + ft.geometry.coordinates[0] + "), 4326),"
                    + "'" + ft.properties.name + "',"
                    + "'" + ft.properties.Date + "',"
                    + "'" + ft.properties.Heure_debut + "',"
                    + "'" + ft.properties.Heure_fin + "',"
                    + "'" + ft.properties.Lieu + "',"
                    + "'" + ft.properties.Camion + "',"
                    + "'" + ft.properties.Truckid + "',"
                    + "'" + ft.properties.description + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.dbConnectionPool.releaseConnection(con);
    }

    
    public static void clearFoodTruck(){
         Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();

            st.executeUpdate("DELETE FROM foodtruck");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Application.dbConnectionPool.releaseConnection(con);
    }
}
