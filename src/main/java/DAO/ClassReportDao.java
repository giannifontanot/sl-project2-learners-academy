package DAO;

import model.Clase;
import model.ClassFull;
import model.SQLState;
import model.Student;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface ClassReportDao {
    public List<ClassFull> getAllStudents() throws SQLException;
    public List<Clase> getAllClasses() throws SQLException;
    public Student fetchOneStudent(String id) throws SQLException;
    public SQLState updateOneStudent(JSONObject jsonObject)throws SQLException;
    public SQLState deleteOneStudent(JSONObject jsonObject)throws SQLException;
    public SQLState saveNewStudent(JSONObject jsonObject)throws SQLException;
}


