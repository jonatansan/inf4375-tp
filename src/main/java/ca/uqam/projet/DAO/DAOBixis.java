/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.DAO;

import ca.uqam.projet.Application;
import ca.uqam.projet.resources.BixiStation;
import ca.uqam.projet.resources.FoodTruck;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jmilot
 */
public class DAOBixis {
    
    public static List<BixiStation> findFrom200m(double lat, double lng){
        List<BixiStation> results = new LinkedList<>();
        
         Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();
            //TODO
            ResultSet rs = st.executeQuery("SELECT *, ST_X(coordinate) as coorX,  ST_Y(coordinate) as coorY from foodtruck"); 
            
            while(rs.next()){
                BixiStation b = new BixiStation();
                
                
                
                results.add(b); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.dbConnectionPool.releaseConnection(con);
        
        return results; 
    }
    
    public static void save(BixiStation b){
        Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();

            st.executeUpdate("INSERT INTO bixi (coordinate, name, nbBikes, nbEmptyDocks) "
                    + " VALUES "
                    + " (ST_SetSRID(ST_MakePoint(" + b.lng + ", " + b.lat + "), 4326),"
                    + "'" + b.name.replace("'", "''") + "',"
                    + b.nbBikes + ","
                    + b.nbEmptyDocks + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.dbConnectionPool.releaseConnection(con);
    }
    
    public static void clearBixi(){
          Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();

            st.executeUpdate("DELETE FROM bixi");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Application.dbConnectionPool.releaseConnection(con);
    }
    
}
