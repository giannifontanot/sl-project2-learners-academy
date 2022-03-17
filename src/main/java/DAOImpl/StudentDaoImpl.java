
package DAOImpl;

import DAO.StudentDao;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Clase;
import model.SQLState;
import model.Student;
import org.json.JSONObject;

public class StudentDaoImpl implements StudentDao {
    List<Student> students = new ArrayList();
    List<Clase> classes = new ArrayList();
    Student student = null;
    Connection conn;

    public StudentDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }

    public List<Student> getAllStudents() throws SQLException {
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("select students.student_id, students.student_name, classes.class_name\n" +
                "from classes, students\n" +
                "where classes.class_id=students.class_id");

        while (rs.next()) {
            int studentId = rs.getInt("student_id");
            String classId = rs.getString("class_name");
            String studentName = rs.getString("student_name");
            Student student = new Student(studentId, classId, studentName);
            this.students.add(student);
            System.out.format(" ----> %s, %s, %s\n", studentId, classId, studentName);
        }

        st.close();
        return this.students;
    }

    public List<Clase> getAllClasses() throws SQLException {
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("select classes.class_id, classes.class_name from classes");

        while (rs.next()) {
            String classId = rs.getString("class_id");
            String className = rs.getString("class_name");
            Clase clase = new Clase(classId, className);
            this.classes.add(clase);
            System.out.format(" ----> %s, %s\n", classId, className);
        }

        st.close();
        return this.classes;
    }

    public Student fetchOneStudent(String id) {
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students where student_id = " + id);
            System.out.println("---> SELECT * FROM students where student_id = " + id);
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String classId = rs.getString("class_id");
                String studentName = rs.getString("student_name");
                student = new Student(studentId, classId, studentName);
            }

            st.close();
        } catch (SQLException e) {
            System.out.println("StudentDaoImp > fetchOneStudent > " + e.getSQLState() + " - " + e.getMessage());
        }
        return this.student;
    }


    @Override
    public SQLState deleteOneStudent(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> deleteOneStudent: ");
        try {
            // SQL String
            String update = " delete from  students  where student_id = ?";
            System.out.println("----> Update: " + update);

            PreparedStatement st = this.conn.prepareStatement(update);
            st.setInt(1, jsonObject.getInt("deleteStudentId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Student deleted successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to delete Student.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
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

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Student updated successfully");
            } else {
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

    @Override
    public SQLState saveNewStudent(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneStudent: ");
        try {
            // SQL String
            String insert = " insert into students (student_id, class_id, student_name) values (?, ?, ?) ";
            System.out.println("----> Update: " + insert);
            PreparedStatement st = this.conn.prepareStatement(insert);

            st.setInt(1, jsonObject.getInt("studentId"));
            st.setString(2, jsonObject.getString("classId"));
            st.setString(3, jsonObject.getString("studentName"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Student created successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to create new Student.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        } catch (Exception err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }
}
