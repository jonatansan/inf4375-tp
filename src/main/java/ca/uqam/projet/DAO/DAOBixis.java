/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.DAO;

import ca.uqam.projet.Application;
import ca.uqam.projet.resources.BixiStation;
import ca.uqam.projet.resources.Distance;
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

    public static List<BixiStation> findFrom200m(double lat, double lng) {
        List<BixiStation> results = new LinkedList<>();

        Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *, ST_X(coordinate) as coorX,  ST_Y(coordinate) as coorY from bixi");

            while (rs.next()) {
                BixiStation b = new BixiStation();

                b.id = rs.getInt("id");
                b.lat = rs.getDouble("coorY");
                b.lng = rs.getDouble("coorX");
                b.name = rs.getString("name");
                b.nbBikes = rs.getInt("nbBikes");
                b.nbEmptyDocks = rs.getInt("nbEmptyDocks");

                if (Distance.inMeter(lat, lng, b.lat, b.lng) < 200) {
                    results.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.dbConnectionPool.releaseConnection(con);

        return results;
    }

    public static void save(BixiStation b) {
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

    public static void clearBixi() {
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
