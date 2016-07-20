/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.DAO;

import ca.uqam.projet.Application;
import ca.uqam.projet.resources.Arceau;
import ca.uqam.projet.resources.BixiStation;
import ca.uqam.projet.resources.Distance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jmilot
 */
public class DAOArceau {

    private static final String STMT_SAVE_ARCEAU = "insert into arceau (coordinate) VALUES ( ST_SetSRID(ST_MakePoint(?, ?), 4326) )";

    public static void save(Arceau a) {
        Connection con = null;
        try {
            con = Application.dbConnectionPool.getConnection();

            PreparedStatement stmt = con.prepareStatement(STMT_SAVE_ARCEAU);
            stmt.setDouble(1, a.lng);
            stmt.setDouble(2, a.lat);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Application.dbConnectionPool.releaseConnection(con);
    }

    private static final String STMT_CLEAN = "delete from arceau";

    public static void clean() {
        Connection con = null;
        try {
            con = Application.dbConnectionPool.getConnection();

            PreparedStatement stmt = con.prepareStatement(STMT_CLEAN);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Application.dbConnectionPool.releaseConnection(con);
    }

    public static List<Arceau> findFrom200m(double lat, double lng) {
        List<Arceau> results = new LinkedList<>();

        Connection con = Application.dbConnectionPool.getConnection();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT *, ST_X(coordinate) as coorX,  ST_Y(coordinate) as coorY from arceau");

            while (rs.next()) {
                Arceau a = new Arceau();
                
                a.id = rs.getInt("id");
                a.lat = rs.getDouble("coorY");
                a.lng = rs.getDouble("coorX");
                
                if (Distance.inMeter(lat, lng, a.lat, a.lng) < 200) {
                    results.add(a);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.dbConnectionPool.releaseConnection(con);

        return results;
    }

}
