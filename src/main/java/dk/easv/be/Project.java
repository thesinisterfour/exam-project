package dk.easv.be;

import java.time.LocalDate;

public class Project {

    private int projectID;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int customerID;
    private String projectAddress;
    private int projectZipcode;

    public Project(int projectID, String projectName, String projectAddress, int projectZipcode) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectAddress = projectAddress;
        this.projectZipcode = projectZipcode;
    }

    public Project(int projectID, String projectName, LocalDate startDate, LocalDate endDate, int customerID, String projectAddress, int projectZipcode) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.projectAddress = projectAddress;
        this.projectZipcode = projectZipcode;
    }

    public Project(String projectName, LocalDate startDate, LocalDate endDate, int customerID, String projectAddress, int projectZipcode) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.projectAddress = projectAddress;
        this.projectZipcode = projectZipcode;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public int getProjectZipcode() {
        return projectZipcode;
    }

    public void setProjectZipcode(int projectZipcode) {
        this.projectZipcode = projectZipcode;
    }

    @Override
    public String toString() {
        return projectName;
    }
}
