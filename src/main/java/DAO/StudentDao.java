package DAO;

import model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    public List<Student> getAllStudents() throws SQLException;
    public Student getOneStudent(String id) throws SQLException;
    public void updateStudent(Student student) throws SQLException;
    public void deleteStudent(Student student) throws SQLException;

}


