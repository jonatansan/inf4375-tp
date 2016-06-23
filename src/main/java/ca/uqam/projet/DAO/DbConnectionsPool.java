package ca.uqam.projet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class DbConnectionsPool {
    
    final private String username; 
    final private String password; 
    final private String url;
    final private String dbdriver;
    final private Queue<Connection> connections = new LinkedList<>();
    
    public DbConnectionsPool(String url, String username, String password, String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.dbdriver = driver;
    }
    
    public synchronized Connection getConnection(){
        try{
            if(connections.isEmpty()){
                Class.forName(dbdriver);
                return DriverManager.getConnection(url, username, password);
            }else{
                Connection c = (Connection) connections.poll();
                if(c==null || c.isClosed())
                    return getConnection();
                return c;
            }
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace(System.err);
        }catch(SQLException sqle){
           sqle.printStackTrace(System.err);
        }
        return null;
    }
    
    public synchronized void releaseConnection(Connection con){
        try{
            if(con!=null && !con.isClosed()){
                connections.offer(con);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace(System.err);
        }
    }
}
