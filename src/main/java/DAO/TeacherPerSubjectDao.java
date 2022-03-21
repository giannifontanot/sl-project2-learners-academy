package DAO;

import model.SQLState;
import model.Subject;
import model.Teacher;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface TeacherPerSubjectDao {
    public List<Subject> getAllSubjects() throws SQLException;
    public List<Teacher> getAllTeachers() throws SQLException;
    public Subject fetchOneSubject(String id) throws SQLException;
    public SQLState updateOneSubject(JSONObject jsonObject)throws SQLException;
    public SQLState deleteOneSubject(JSONObject jsonObject)throws SQLException;
    public SQLState saveNewSubject(JSONObject jsonObject)throws SQLException;
}


