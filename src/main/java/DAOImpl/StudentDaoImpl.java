
package DAOImpl;

import DAO.StudentDao;
import db.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Student;

public class StudentDaoImpl implements StudentDao {
    List<Student> students = new ArrayList();
    Student student = null;
    Connection conn;

    public StudentDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }

    public List<Student> getAllStudents() throws SQLException {
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM students");

        while(rs.next()) {
            int studentId = rs.getInt("student_id");
            String classId = rs.getString("class_id");
            String studentName = rs.getString("student_name");
            Student student = new Student(studentId, classId, studentName);
            this.students.add(student);
            System.out.format(" ----> %s, %s, %s\n", studentId, classId, studentName);
        }

        st.close();
        return this.students;
    }

    public Student getOneStudent(String id) throws SQLException {

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM students where student_id = " + id);

        while(rs.next()) {
            int studentId = rs.getInt("student_id");
            String classId = rs.getString("class_id");
            String studentName = rs.getString("student_name");
            student = new Student(studentId, classId, studentName);
        }

        st.close();
        return this.student;
    }

    public void updateStudent(Student student) {
        ((Student)this.students.get(student.getStudentId())).setStudentName(student.getStudentName());
        System.out.println("Student: ID No " + student.getStudentId() + ", updated in the database");
    }

    public void deleteStudent(Student student) {
        this.students.remove(student.getStudentId());
    }
}
