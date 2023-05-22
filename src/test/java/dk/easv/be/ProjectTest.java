package dk.easv.be;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ProjectTest {
    @Test
    public void testConstructor() {
        Project project = new Project(1, "Project1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1), 1234, "Address1", 1234);
        Assertions.assertEquals(1, project.getProjectID());
        Assertions.assertEquals("Project1", project.getProjectName());
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), project.getStartDate());
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), project.getEndDate());
        Assertions.assertEquals(1234, project.getCustomerID());
        Assertions.assertEquals("Address1", project.getProjectAddress());
        Assertions.assertEquals(1234, project.getProjectZipcode());
    }

    @Test
    public void testProjectID() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        Assertions.assertEquals(1, project.getProjectID());
        project.setProjectID(2);
        Assertions.assertEquals(2, project.getProjectID());
    }

    @Test
    public void testProjectName() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        Assertions.assertEquals("Project1", project.getProjectName());
        project.setProjectName("NewProject");
        Assertions.assertEquals("NewProject", project.getProjectName());
    }

    @Test
    public void testStartDate() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        project.setStartDate(startDate);
        Assertions.assertEquals(startDate, project.getStartDate());
    }

    @Test
    public void testEndDate() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        project.setEndDate(endDate);
        Assertions.assertEquals(endDate, project.getEndDate());
    }

    @Test
    public void testCustomerID() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        project.setCustomerID(2);
        Assertions.assertEquals(2, project.getCustomerID());
    }

    @Test
    public void testProjectAddress() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        project.setProjectAddress("NewAddress");
        Assertions.assertEquals("NewAddress", project.getProjectAddress());
    }

    @Test
    public void testProjectZipcode() {
        Project project = new Project(1, "Project1", "Address1", 1234);
        project.setProjectZipcode(5678);
        Assertions.assertEquals(5678, project.getProjectZipcode());
    }
}
