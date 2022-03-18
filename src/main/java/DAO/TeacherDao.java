package DAO;

import model.Clase;
import model.SQLState;
import model.Teacher;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface TeacherDao {
    public List<Teacher> getAllTeachers() throws SQLException;
    public Teacher fetchOneTeacher(String id) throws SQLException;
    public SQLState updateOneTeacher(JSONObject jsonObject)throws SQLException;
    public SQLState deleteOneTeacher(JSONObject jsonObject)throws SQLException;
    public SQLState saveNewTeacher(JSONObject jsonObject)throws SQLException;
}