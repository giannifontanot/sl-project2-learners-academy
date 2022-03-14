
package DAOImpl;

import DAO.StudentDao;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.SQLState;
import model.Student;
import org.json.JSONObject;

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

        while (rs.next()) {
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

    public Student fetchOneStudent(String id) throws SQLException {

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM students where student_id = " + id);

        while (rs.next()) {
            int studentId = rs.getInt("student_id");
            String classId = rs.getString("class_id");
            String studentName = rs.getString("student_name");
            student = new Student(studentId, classId, studentName);
        }

        st.close();
        return this.student;
    }


    public void deleteStudent(Student student) {
        this.students.remove(student.getStudentId());
    }

    @Override
    public SQLState updateOneStudent(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneStudent: ");
        try {
            // SQL String
            String update = " update students  set class_id = ?, student_name = ? where student_id = ?";
            System.out.println("----> Update: " + update);
            PreparedStatement st = this.conn.prepareStatement(update);

            st.setString(1, jsonObject.getString("classId"));
            st.setString(2, jsonObject.getString("studentName"));
            st.setInt(3, jsonObject.getInt("studentId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if(code>0){
                sqlState.setCode(0);
                sqlState.setMessage("Student updated successfully");
            }else{
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to update Student.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }
}
