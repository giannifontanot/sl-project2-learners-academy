package DAO;

import model.ClassSubject;

import java.sql.SQLException;
import java.util.List;

public interface ClassSubjectDao {
    public List<ClassSubject> getAllClassSubjects() throws SQLException;
}


