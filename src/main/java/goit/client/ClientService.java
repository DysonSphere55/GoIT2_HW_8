package goit.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement getMaxIdSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement setNameSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement getAllSt;


    public ClientService(Connection connection) {
        try {
            createSt = connection.prepareStatement("""
                    INSERT INTO client (name) VALUES (?);""");
            getMaxIdSt = connection.prepareStatement("""
                    SELECT max(id) as maxId FROM client;""");
            getByIdSt = connection.prepareStatement("""
                    SELECT name FROM client WHERE id = ?;""");
            setNameSt = connection.prepareStatement("""
                    UPDATE client SET name = ? WHERE id = ?;""");
            deleteByIdSt = connection.prepareStatement("""                             
                    DELETE FROM project_worker WHERE project_id IN (
                        SELECT id FROM project
                        WHERE client_id = ?);
                    DELETE FROM project WHERE client_id = ?;
                    DELETE FROM client WHERE id = ?;""");
            getAllSt = connection.prepareStatement("""
                    SELECT id, name FROM client;""");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long create(String name) throws IncorrectLengthException {
        if (name.length() <= 2 || name.length() >= 1000) {
            throw new IncorrectLengthException();
        }
        long id = -1;
        try {
            createSt.setString(1, name);
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

    public String getById(long id) {
        String result = "";
        try {
            getByIdSt.setLong(1, id);
            try (ResultSet rs = getByIdSt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setName(long id, String name) throws IncorrectLengthException {
        if (name.length() <= 2 || name.length() >= 1000) {
            throw new IncorrectLengthException();
        }
        try {
            setNameSt.setString(1, name);
            setNameSt.setLong(2, id);
            setNameSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(long id) {
        try {
            for (int i = 1; i <= 3; i++) {
                deleteByIdSt.setLong(i, id);
            }
            deleteByIdSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> listAll() {
        List<Client> result = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                result.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
