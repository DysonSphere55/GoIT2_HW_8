package goit.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getMaxIdSt;
    private PreparedStatement getByIdSt;

    public ClientDaoService(Connection connection) {
        try {
            createSt = connection.prepareStatement("""
                    INSERT INTO client (name) VALUES (?);""");
            getMaxIdSt = connection.prepareStatement("""
                    SELECT max(id) as maxId FROM client;""");
            getByIdSt = connection.prepareStatement("""
                    SELECT name FROM client WHERE id = ?;""");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long create(ClientEntity client) {
        long id = -1;
        try {
            createSt.setString(1, client.getName());
            createSt.executeUpdate();
            try (ResultSet rs = getMaxIdSt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getLong("maxId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ClientEntity getById(long id) {
        ClientEntity result = new ClientEntity();
        try {
            getByIdSt.setLong(1, id);
            try (ResultSet rs = getByIdSt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                result.setId(id);
                result.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
