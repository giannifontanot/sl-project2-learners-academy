package DAO;

import model.Clase;
import model.SQLState;
import model.Student;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    public List<Student> getAllStudents() throws SQLException;
    public List<Clase> getAllClasses() throws SQLException;
    public Student fetchOneStudent(String id) throws SQLException;
    public void deleteStudent(Student student) throws SQLException;
    public SQLState updateOneStudent(JSONObject jsonObject)throws SQLException;
}


