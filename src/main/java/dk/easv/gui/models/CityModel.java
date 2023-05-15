package dk.easv.gui.models;

import dk.easv.be.City;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.models.interfaces.ICityModel;

import java.sql.SQLException;

public class CityModel implements ICityModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();

    @Override
    public int add(City city) throws SQLException{
        return crudLogic.addCity(city);
    }

    @Override
    public City get(int zipcode) throws SQLException{
        return crudLogic.getCity(zipcode);
    }
}
