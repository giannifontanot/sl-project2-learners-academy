package DAO;

import model.Clase;
import model.SQLState;
import model.Subject;
import model.SubjectPerClass;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectsPerClassDao {
    public List<SubjectPerClass> getAllSubjectsPerClass(Clase clase) throws SQLException;
    public List<Clase> getAllClases() throws SQLException;
    public List<Subject>  getAllSubjectsAvailable() throws SQLException;
    public SQLState deleteOneSubjectPerClass(JSONObject jsonObject)throws SQLException;
    public SQLState saveNewSubjectPerClass(JSONObject jsonObject)throws SQLException;

}


