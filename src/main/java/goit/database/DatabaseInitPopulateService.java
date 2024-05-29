package goit.database;

import goit.prefs.Prefs;
import org.flywaydb.core.Flyway;


public class DatabaseInitPopulateService {

    public void initPopulate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                new Prefs().getValue(Prefs.DB_URL_CONNECTION), null, null)
                .load();

        flyway.migrate();
    }

    public static void main(String[] args) {
        DatabaseInitPopulateService dbInitPopulateService = new DatabaseInitPopulateService();
        dbInitPopulateService.initPopulate();
    }
}
