import goit.client.Client;
import goit.client.ClientService;
import goit.client.IncorrectLengthException;
import goit.database.Database;

import java.sql.Connection;
import java.util.List;

public class App {
    public static void main(String[] args) {

        Connection connection = Database.getConnection();
        ClientService clientService = new ClientService(connection);

        List<Client> clients = clientService.listAll();
        clients.forEach(System.out::println);

        try {
            clientService.create("Heart of Darkness Inc.");
            clientService.setName(3, "Sarif Industries");
        } catch (IncorrectLengthException e) {
            e.printStackTrace();
        }

        clientService.deleteById(2L);

        String client = clientService.getById(6);
        System.out.println("client = " + client);

        clients = clientService.listAll();
        clients.forEach(System.out::println);
    }
}
