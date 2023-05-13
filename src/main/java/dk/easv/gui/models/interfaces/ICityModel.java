package dk.easv.gui.models.interfaces;

import dk.easv.be.City;

import java.sql.SQLException;

public interface ICityModel {
    int add(City city) throws SQLException;

    City get(int zipcode) throws SQLException;
}
