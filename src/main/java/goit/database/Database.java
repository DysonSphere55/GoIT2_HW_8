package goit.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import goit.prefs.Prefs;

import java.sql.Connection;
import java.sql.SQLException;


public class Database {

    private static final Prefs prefs = new Prefs();
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(prefs.getValue(Prefs.DB_URL_CONNECTION));
        ds = new HikariDataSource(config);
    }

    private Database() {
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
