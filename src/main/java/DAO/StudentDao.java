package DAO;

import model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    public List<Student> getAllStudents() throws SQLException;
    public Student getStudent(int id);
    public void updateStudent(Student student);
    public void deleteStudent(Student student);

}


