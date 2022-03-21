package DAO;

import model.Clase;
import model.SQLState;
import model.User;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface LoginDao {
    public boolean verifyCredentials(User user) throws SQLException;

}


