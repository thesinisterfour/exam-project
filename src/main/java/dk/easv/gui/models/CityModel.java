package dk.easv.gui.models;

import dk.easv.be.City;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;

public class CityModel {

    private CRUDLogic crudLogic = new CRUDLogic();

    public int add(City city) throws SQLException{
        return crudLogic.addCity(city);
    }

    public City get(int zipcode) throws SQLException{
        return crudLogic.getCity(zipcode);
    }
}
