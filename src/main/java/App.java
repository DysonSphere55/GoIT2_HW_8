import goit.database.Database;
import goit.database.DatabasePopulateService;
import goit.database.DatabaseQueryService;
import goit.pojo.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {

        Connection connection = Database.getConnection();

        DatabasePopulateService dbPopulateService = new DatabasePopulateService(connection);

        Worker newWorker = new Worker(
                "Mark Manson",
                LocalDate.now().minusYears(33),
                "Middle",
                3500
        );

        dbPopulateService.insertWorker(newWorker);
        System.out.println("newWorker = " + newWorker);


        Client newClient = new Client("Magnum Inc.");
        dbPopulateService.insertClient(newClient);
        System.out.println("newClient = " + newClient);

        Project newProject = new Project(
                6,
                "Project X",
                LocalDate.now().minusMonths(7),
                LocalDate.now());
        dbPopulateService.insertProject(newProject);
        System.out.println("newProject = " + newProject);

        ProjectToWorkersRelation projectToWorkerRelation = new ProjectToWorkersRelation(
                11,
                new long[]{11, 10, 9}
        );
        dbPopulateService.insertProjectToWorkerRelation(projectToWorkerRelation);
        System.out.println("projectToWorkerRelation = " + projectToWorkerRelation);

        DatabaseQueryService dbQueryService = new DatabaseQueryService(connection);

        List<LongestProject> longestProjectList = dbQueryService.findLongestProject();
        longestProjectList.forEach(System.out::println);

        List<MaxSalaryWorker> maxSalaryWorkerList = dbQueryService.findMaxSalaryWorker();
        maxSalaryWorkerList.forEach(System.out::println);

        List<MaxProjectsClient> maxProjectsClientList = dbQueryService.findMaxProjectsClient();
        maxProjectsClientList.forEach(System.out::println);

        List<EldestWorker> eldestWorkerList = dbQueryService.findEldestWorker();
        eldestWorkerList.forEach(System.out::println);

        List<YoungestWorker> youngestWorkerList = dbQueryService.findYoungestWorker();
        youngestWorkerList.forEach(System.out::println);

        List<ProjectPrice> projectPriceList = dbQueryService.getProjectPrices();
        projectPriceList.forEach(System.out::println);
    }
}
