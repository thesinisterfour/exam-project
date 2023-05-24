package dk.easv.gui.controllers.helpers;

import dk.easv.gui.models.CityModel;
import dk.easv.gui.models.interfaces.ICityModel;
import dk.easv.helpers.AlertHelper;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ZipCodeChecker {
    public static int checkZipCode(String zip) {
        ICityModel cityModel = new CityModel();
        try {
            int zipCode = Integer.parseInt(zip);
            cityModel.get(zipCode);
            return zipCode;
        } catch (SQLException e) {
            // catch if city does not exist
            AlertHelper alertHelper = new AlertHelper("City does not exist", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
            return 0;
        } catch (Exception e){
            // catch if zip is not a number
            AlertHelper alertHelper = new AlertHelper("Zip code must be a number", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
            return 0;
        }

    }
}
