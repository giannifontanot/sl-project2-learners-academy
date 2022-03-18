package DAO;

import model.Clase;
import model.SQLState;
import model.Student;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface ClaseDao {
    public List<Clase> getAllClasses() throws SQLException;
    public Clase fetchOneClass(String id) throws SQLException;
    public SQLState updateOneClass(JSONObject jsonObject)throws SQLException;
    public SQLState deleteOneClass(JSONObject jsonObject)throws SQLException;
    public SQLState saveNewClass(JSONObject jsonObject)throws SQLException;
}


