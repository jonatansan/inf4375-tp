package ca.uqam.projet;

import ca.uqam.projet.DAO.DbConnectionsPool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    public static final Application instance = new Application();
    public static DbConnectionsPool dbConnectionPool;
    
    
    public static void main(String[] args) {
        dbConnectionPool = new DbConnectionsPool("jdbc:postgresql://localhost:5432/tp", "inf4375", "inf4375", "org.postgresql.Driver"); 
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
